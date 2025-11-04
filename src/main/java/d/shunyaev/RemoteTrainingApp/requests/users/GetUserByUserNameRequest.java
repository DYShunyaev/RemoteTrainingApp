package d.shunyaev.RemoteTrainingApp.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUserByUserNameRequest {

    @JsonProperty("user_name")
    private String userName;
}
