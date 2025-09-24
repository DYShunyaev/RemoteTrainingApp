package d.shunyaev.RemoteTrainingApp.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SetTrainerRequest {

    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("trainer_id")
    private long trainerId;
}
