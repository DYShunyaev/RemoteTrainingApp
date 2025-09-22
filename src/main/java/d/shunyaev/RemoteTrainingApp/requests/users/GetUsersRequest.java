package d.shunyaev.RemoteTrainingApp.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetUsersRequest {
    @JsonProperty("user_id")
    private List<Long> userId;

}
