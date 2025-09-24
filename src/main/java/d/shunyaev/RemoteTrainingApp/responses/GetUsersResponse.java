package d.shunyaev.RemoteTrainingApp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import d.shunyaev.RemoteTrainingApp.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersResponse {

    private List<UserData> users;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class UserData {
        @JsonProperty("user_id")
        private long userId;
        @JsonProperty("user_name")
        private String userName;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String gender;
        private String email;
        @JsonProperty("date_of_birth")
        private LocalDate dateOfBirth;
        private int age;
        private String role;
        @JsonProperty("training_level")
        private String trainingLevel;
        private String goals;
        private long weight;
        private long height;

    }

}
