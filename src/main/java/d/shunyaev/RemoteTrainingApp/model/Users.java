package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.enums.Gender;
import d.shunyaev.RemoteTrainingApp.enums.Role;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public void setGender(String gender) {
        this.gender = gender != null
                ? Arrays.stream(Gender.values())
                .filter(gen -> gen.getDescription().equals(gender))
                .findFirst()
                .orElse(Gender.NON)
                : Gender.NON;
    }

    public static class Builder {
        private long id;
        private String userName;
        private String firstName;
        private String lastName;
        private Role roles;
        private Gender gender;
        private String email;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder roles(Role roles) {
            this.roles = roles;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Users build() {
            Users user = new Users();
            return build(user);
        }

        public Users build(Users user) {
            user.setId(id);
            user.setUserName(this.userName);
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setRole(this.roles);
            user.setGender(this.gender != null ? this.gender.getDescription() : null);
            user.setEmail(this.email);
            return user;
        }
    }
}