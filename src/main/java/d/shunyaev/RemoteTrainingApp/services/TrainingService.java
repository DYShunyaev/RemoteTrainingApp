package d.shunyaev.RemoteTrainingApp.services;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.components.ValidateComponent;
import d.shunyaev.RemoteTrainingApp.model.Exercise;
import d.shunyaev.RemoteTrainingApp.model.Result;
import d.shunyaev.RemoteTrainingApp.model.Training;
import d.shunyaev.RemoteTrainingApp.model.Users;
import d.shunyaev.RemoteTrainingApp.repositories.ExerciseRepository;
import d.shunyaev.RemoteTrainingApp.repositories.TrainingRepository;
import d.shunyaev.RemoteTrainingApp.repositories.UserInfoRepository;
import d.shunyaev.RemoteTrainingApp.repositories.UserRepository;
import d.shunyaev.RemoteTrainingApp.requests.trainings.CreateExerciseRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.CreateTrainingRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.GetTrainingsRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.GetTrainingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainingService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public TrainingService(UserRepository userRepository, UserInfoRepository userInfoRepository,
                           TrainingRepository trainingRepository, ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public Result createNewTraining(CreateTrainingRequest request) {
        ValidateComponent.notNull(
                request.getUserId(),
                request.getDayOfWeek(),
                request.getDate());

        ValidateComponent.dayOfWeekValidation(request.getDayOfWeek());

        Users user = userRepository.getUserById(request.getUserId());

        Training training = new Training()
                .setUserId(user.getId())
                .setDate(request.getDate())
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
                            .setExercises(exercisesList);
                })
                .toList();
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
}
