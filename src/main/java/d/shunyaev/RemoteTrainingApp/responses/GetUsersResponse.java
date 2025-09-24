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

        public static class UserDataBuilder {
            private long userId;
            private String userName;
            private String firstName;
            private String lastName;
            private String gender;
            private String email;
            private LocalDate dateOfBirth;
            private int age;
            private String role;
            private String trainingLevel;
            private String goals;
            private long weight;
            private long height;

            public UserDataBuilder userId(long userId) {
                this.userId = userId;
                return this;
            }

            public UserDataBuilder userName(String userName) {
                this.userName = userName;
                return this;
            }

            public UserDataBuilder firstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public UserDataBuilder lastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public UserDataBuilder gender(String gender) {
                this.gender = gender;
                return this;
            }

            public UserDataBuilder email(String email) {
                this.email = email;
                return this;
            }

            public UserDataBuilder dateOfBirth(LocalDate dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
                return this;
            }

            public UserDataBuilder age(int age) {
                this.age = age;
                return this;
            }

            public UserDataBuilder role(String role) {
                this.role = role;
                return this;
            }

            public UserDataBuilder trainingLevel(String trainingLevel) {
                this.trainingLevel = trainingLevel;
                return this;
            }

            public UserDataBuilder goals(String goals) {
                this.goals = goals;
                return this;
            }

            public UserDataBuilder weight(long weight) {
                this.weight = weight;
                return this;
            }

            public UserDataBuilder height(long height) {
                this.height = height;
                return this;
            }

            public UserData build() {
                UserData userData = new UserData();

                userData.setUserId(this.userId);
                userData.setUserName(this.userName);
                userData.setFirstName(this.firstName);
                userData.setLastName(this.lastName);
                userData.setGender(this.gender);
                userData.setEmail(this.email);
                userData.setDateOfBirth(this.dateOfBirth);
                userData.setAge(this.age);
                userData.setRole(this.role);
                userData.setTrainingLevel(this.trainingLevel);
                userData.setGoals(this.goals);
                userData.setWeight(this.weight);
                userData.setHeight(this.height);

                return userData;
            }
        }
    }

}
