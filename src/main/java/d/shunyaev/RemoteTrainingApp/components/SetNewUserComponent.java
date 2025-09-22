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
        return new UserInfo.Builder()
                .height(request.getHeight())
                .weight(request.getWeight())
                .dateOfBirth(request.getDateOfBirth())
                .userName(userName)
                .age(Period.between(request.getDateOfBirth(), currentDay).getYears())
                .goals((Goals) SupportComponent.getEnumValue(Goals.values(), request.getGoals()))
                .trainingLevel((TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), request.getTrainingLevel()))
                .build();
    }

    public Users createUser(CreateUserRequest request) {
        return new Users.Builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .roles(request.getIsTrainer() == 1 ? Role.TRAINER : Role.USER)
                .gender((Gender) SupportComponent.getEnumValue(Gender.values(), request.getGender()))
                .email(request.getEmail())
                .build();
    }
}
