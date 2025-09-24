package d.shunyaev.RemoteTrainingApp.controllers;

import d.shunyaev.RemoteTrainingApp.model.Result;
import d.shunyaev.RemoteTrainingApp.requests.trainings.CreateExerciseRequest;
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
public class ExerciseController extends AbstractController {

    private static final String SET_EXERCISE = API_PREFIX + "/create_exercise";

    private final TrainingService trainingService;

    @Autowired
    public ExerciseController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(summary = "Сервис создания упражнений")
    @PostMapping(SET_EXERCISE)
    public ResponseContainer<Result> createExercise(@NotNull @RequestBody RequestContainer<CreateExerciseRequest> requestContainer) {
        return call(trainingService::createNewExercise, requestContainer);
    }
}
