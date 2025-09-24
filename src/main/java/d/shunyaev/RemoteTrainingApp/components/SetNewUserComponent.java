package d.shunyaev.RemoteTrainingApp.components;

import d.shunyaev.RemoteTrainingApp.enums.*;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import d.shunyaev.RemoteTrainingApp.model.Users;
import d.shunyaev.RemoteTrainingApp.requests.users.CreateUserRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;

@UtilityClass
public class SetNewUserComponent {

    public UserInfo createUserInfo(CreateUserRequest request, LocalDate currentDay, String userName) {
        return new UserInfo()
                .setHeight(request.getHeight())
                .setWeight(request.getWeight())
                .setDateOfBirth(request.getDateOfBirth())
                .setUserName(userName)
                .setAge(Period.between(request.getDateOfBirth(), currentDay).getYears())
                .setGoals((Goals) SupportComponent.getEnumValue(Goals.values(), request.getGoals()))
                .setTrainingLevel((TrainingLevel)
                        SupportComponent.getEnumValue(TrainingLevel.values(), request.getTrainingLevel()));
    }

    public Users createUser(CreateUserRequest request) {
        return new Users()
                .setUserName(request.getUserName())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setRole(request.getIsTrainer() == 1 ? Role.TRAINER : Role.USER)
                .setEmail(request.getEmail())
                .setGender(request.getGender());
    }
}
