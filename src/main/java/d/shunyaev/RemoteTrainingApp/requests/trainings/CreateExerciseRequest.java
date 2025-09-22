package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateExerciseRequest {

    private long trainingId;
    @Schema(
            defaultValue = "Жим лежа",
            description = "Название упражнения"
    )
    @JsonProperty("exercise_name")
    private String exerciseName;
    @Schema(
            defaultValue = "10",
            description = "Количество повторений"
    )
    private int quantity;
    @Schema(
            defaultValue = "3",
            description = "Количество подходов"
    )
    private int approach;
}
