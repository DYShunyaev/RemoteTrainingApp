package d.shunyaev.RemoteTrainingApp.services;

import d.shunyaev.RemoteTrainingApp.components.GptComponent;
import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.components.ValidateComponent;
import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import d.shunyaev.RemoteTrainingApp.model.*;
import d.shunyaev.RemoteTrainingApp.repositories.ExerciseRepository;
import d.shunyaev.RemoteTrainingApp.repositories.TrainingRepository;
import d.shunyaev.RemoteTrainingApp.repositories.UserInfoRepository;
import d.shunyaev.RemoteTrainingApp.repositories.UserRepository;
import d.shunyaev.RemoteTrainingApp.requests.CompletionRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.*;
import d.shunyaev.RemoteTrainingApp.utils.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TrainingService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final GptService gptService;

    @Autowired
    public TrainingService(UserRepository userRepository, UserInfoRepository userInfoRepository,
                           TrainingRepository trainingRepository, ExerciseRepository exerciseRepository,
                           GptService gptService) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
        this.gptService = gptService;
    }

    @Transactional
    public Result createNewTraining(CreateTrainingRequest request) {
        ValidateComponent.notNull(
                request.getUserId(),
                request.getDayOfWeek(),
                request.getDate(),
                request.getMuscleGroup());
        ValidateComponent.validateDate(request.getDate());
        ValidateComponent.dayOfWeekValidation(request.getDayOfWeek());

        Users user = userRepository.getUserById(request.getUserId());

        Training training = new Training()
                .setUserId(user.getId())
                .setDate(request.getDate())
                .setMuscleGroup(request.getMuscleGroup())
                .setDayOfWeek(SupportComponent.compareRusToDayOfWeek(request.getDayOfWeek()));

        trainingRepository.setNewTraining(training);
        return new Result(Result.Message.SUCCESS);
    }

    public GetTrainingsResponse getTrainings(GetTrainingsRequest request) {
        ValidateComponent.notNull(request.getUserId());

        List<Training> trainings = trainingRepository.getTrainingsByUserId(request.getUserId());

        List<GetTrainingsResponse.Trainings> trainingsResponse = trainings
                .stream()
                .map(training -> {
                    List<GetTrainingsResponse.Exercises> exercisesList =
                            exerciseRepository.getExercisesByTrainingId(training.getId())
                                    .stream()
                                    .map(exercise -> new GetTrainingsResponse.Exercises()
                                            .setApproach(exercise.getApproach())
                                            .setQuantity(exercise.getQuantity())
                                            .setExerciseName(exercise.getExerciseName()))
                                    .toList();

                    return new GetTrainingsResponse.Trainings()
                            .setDate(training.getDate())
                            .setDayOfWeek(SupportComponent.dayOfWeekToRus(training.getDayOfWeek()))
                            .setIsDone(training.getIsDone())
                            .setMuscleGroup(training.getMuscleGroup())
                            .setExercises(exercisesList);
                })
                .sorted(Comparator.comparing(GetTrainingsResponse.Trainings::getDate))
                .toList();

        if (Objects.nonNull(request.getDateOfTraining())) {
            ValidateComponent.validateDate(request.getDateOfTraining());
            trainingsResponse = List.of(trainingsResponse
                    .stream()
                    .filter(train -> train.getDate().equals(request.getDateOfTraining()))
                    .findFirst()
                    .orElseThrow(() -> LogicException.of(ResponseCode.TRAINING_IS_NOT_FOUND,
                            request.getDateOfTraining())));
        }
        return new GetTrainingsResponse()
                .setTrainings(trainingsResponse);
    }

    @Transactional
    public Result createNewExercise(CreateExerciseRequest request) {
        ValidateComponent.notNull(
                request.getTrainingId(),
                request.getExerciseName(),
                request.getQuantity(),
                request.getApproach()
        );

        Training training = trainingRepository.getTrainingById(request.getTrainingId());

        Exercise exercise = new Exercise()
                .setTrainingId(training.getId())
                .setExerciseName(request.getExerciseName())
                .setQuantity(request.getQuantity())
                .setApproach(request.getApproach());

        exerciseRepository.setExercise(exercise);
        return new Result(Result.Message.SUCCESS);
    }

    @Transactional
    public Result trainingIsDone(TrainingIsDoneRequest request) {
        ValidateComponent.notNull(
                request.getTrainingDate(),
                request.getUserId()
        );
        if (!userRepository.userIsExist(request.getUserId())) {
            throw LogicException.of(ResponseCode.NOT_FOUND_USER, request.getUserId());
        }
        Training training = trainingRepository.getTrainingsByUserId(request.getUserId())
                .stream()
                .filter(t -> t.getDate().equals(request.getTrainingDate()))
                .findFirst()
                .orElseThrow(() -> LogicException.of(ResponseCode.TRAINING_IS_NOT_FOUND,
                        request.getTrainingDate()));

        trainingRepository.updateTraining(
                new Training()
                        .setId(training.getId())
                        .setIsDone(true)
        );
        return new Result(Result.Message.SUCCESS);
    }

    @Transactional
    public Result generateTraining(GenerateTrainingRequest request) {
        ValidateComponent.notNull(request.getUserId());
        ValidateComponent.dayOfWeekValidation(request.getDayOfWeekFirstTraining());
        ValidateComponent.validateDate(request.getDateFirstTraining());
        ValidateComponent.objectMoreZero(request.getCount());

        Users user = userRepository.getUserById(request.getUserId());
        UserInfo userInfo = userInfoRepository.getUserInfoByUserName(user.getUserName());

        String prompt = GptComponent.getPrompt(request.getCount(), user, userInfo, request.getDayOfWeekFirstTraining());

        CompletionRequest completionRequest = new CompletionRequest()
                .setMaxTokens(3000)
                .setPrompt(prompt)
                .setStream(false);

        String responseFromGpt = gptService.sendCompletionRequest(completionRequest);

        var trainingsMap = GptComponent.parseTrainings(responseFromGpt, user.getId(), request.getDateFirstTraining());

        for (Map.Entry<Training, List<Exercise>> trainings : trainingsMap.entrySet()) {
            trainingRepository.setNewTraining(trainings.getKey());
            Training training = trainingRepository.getTrainingsByUserId(user.getId())
                    .stream()
                    .filter(t -> t.equalsWithIgnoreId(trainings.getKey()))
                    .findFirst()
                    .orElseThrow(() -> LogicException.of(ResponseCode.INVALID_VALUE));

            for (Exercise exercises : trainings.getValue()) {
                exerciseRepository.setExercise(
                        exercises
                                .setTrainingId(training.getId()));
            }
        }
        return new Result(Result.Message.SUCCESS);
    }
}
