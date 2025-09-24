package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingIsDoneRequest {

    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("training_date")
    private LocalDate trainingDate;
}
