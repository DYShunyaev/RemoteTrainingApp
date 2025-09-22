package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.enums.Goals;
import d.shunyaev.RemoteTrainingApp.enums.TrainingLevel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserInfo {

    private Long id;
    private Long weight;
    private Long height;
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private String userName;
    private int age;
    private Goals goals;
    @JsonProperty("training_level")
    private TrainingLevel trainingLevel;

    public static class Builder {
        private Long id;
        private Long weight;
        private Long height;
        private LocalDate dateOfBirth;
        private String userName;
        private int age;
        private Goals goals;
        private TrainingLevel trainingLevel;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder weight(Long weight) {
            this.weight = weight;
            return this;
        }

        public Builder height(Long height) {
            this.height = height;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder goals(Goals goals) {
            this.goals = goals;
            return this;
        }

        public Builder trainingLevel(TrainingLevel trainingLevel) {
            this.trainingLevel = trainingLevel;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public UserInfo build() {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(this.id);
            userInfo.setHeight(this.height);
            userInfo.setWeight(this.weight);
            userInfo.setDateOfBirth(this.dateOfBirth);
            userInfo.setUserName(userName);
            userInfo.setAge(age);
            userInfo.setGoals(goals);
            userInfo.setTrainingLevel(trainingLevel);
            return userInfo;
        }
    }
}
