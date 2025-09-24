package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.enums.Gender;
import d.shunyaev.RemoteTrainingApp.enums.Role;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Users {

    private Long id;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private Role role;
    private Gender gender;
    private String email;
}