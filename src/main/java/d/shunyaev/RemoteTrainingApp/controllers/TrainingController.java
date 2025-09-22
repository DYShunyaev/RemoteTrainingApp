package d.shunyaev.RemoteTrainingApp.controllers;

import d.shunyaev.RemoteTrainingApp.model.Result;
import d.shunyaev.RemoteTrainingApp.requests.trainings.CreateTrainingRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.GetTrainingsRequest;
import d.shunyaev.RemoteTrainingApp.requests.trainings.GetTrainingsResponse;
import d.shunyaev.RemoteTrainingApp.requests.users.GetUsersRequest;
import d.shunyaev.RemoteTrainingApp.responses.GetUsersResponse;
import d.shunyaev.RemoteTrainingApp.services.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.core.containers.RequestContainer;
import org.core.containers.ResponseContainer;
import org.core.controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController extends AbstractController {

    private static final String SET_TRAINING = API_PREFIX + "/training";
    private static final String GET_TRAINING = API_PREFIX + "/get_training";

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(summary = "Сервис создания тренировки")
    @PostMapping(SET_TRAINING)
    public ResponseContainer<Result> createTraining(@NotNull @RequestBody RequestContainer<CreateTrainingRequest> requestContainer) {
        return call(trainingService::createNewTraining, requestContainer);
    }

    @Operation(summary = "Сервис получения информации о тренировках пользователя")
    @PostMapping(GET_TRAINING)
    public ResponseContainer<GetTrainingsResponse> getTrainings(@NotNull @RequestBody RequestContainer<GetTrainingsRequest> requestContainer) {
        return call(trainingService::getTrainings, requestContainer);
    }
}
