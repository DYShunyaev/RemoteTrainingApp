package d.shunyaev.RemoteTrainingApp.services;

import d.shunyaev.RemoteTrainingApp.components.SetNewUserComponent;
import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.components.ValidateComponent;
import d.shunyaev.RemoteTrainingApp.enums.Goals;
import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.enums.Role;
import d.shunyaev.RemoteTrainingApp.enums.TrainingLevel;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import d.shunyaev.RemoteTrainingApp.model.Result;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import d.shunyaev.RemoteTrainingApp.model.Users;
import d.shunyaev.RemoteTrainingApp.repositories.UserInfoRepository;
import d.shunyaev.RemoteTrainingApp.repositories.UserRepository;
import d.shunyaev.RemoteTrainingApp.requests.CreateUserRequest;
import d.shunyaev.RemoteTrainingApp.requests.GetUsersRequest;
import d.shunyaev.RemoteTrainingApp.requests.UpdateUserRequest;
import d.shunyaev.RemoteTrainingApp.responses.GetUsersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices extends AbstractService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    public UserServices(UserRepository userRepository, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Transactional
    public Result setNewUser(CreateUserRequest createUserRequest) {
        ValidateComponent.notNull(
                createUserRequest.getUserName(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName()
        );

        ValidateComponent.defaultValue(
                createUserRequest.getUserName(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getDateOfBirth()
        );

        ValidateComponent.validateEmail(createUserRequest.getEmail());
        ValidateComponent.validateBirthDate(createUserRequest.getDateOfBirth());

        if (!userRepository.isUniqueUserName(createUserRequest.getUserName())) {
            throw LogicException.of(ResponseCode.USER_NAME_NOT_UNIQUE, createUserRequest.getUserName());
        }

        Users user = SetNewUserComponent.createUser(createUserRequest);
        UserInfo userInfo = SetNewUserComponent.createUserInfo(createUserRequest, currentDay, user);

        userRepository.setUser(user);
        userInfoRepository.setUserInfo(userInfo);
        return new Result(Result.Message.SUCCESS);
    }

    public Result updateUser(UpdateUserRequest request) {
        ValidateComponent.notNull(
                request.getUserId()
        );

        Users existingUser = userRepository.getUserById(request.getUserId());
        ValidateComponent.userNotNull(existingUser);

        UserInfo existinguserInfo = userInfoRepository.getUserInfoByUserId(request.getUserId());

        if (request.getEmail() != null) {
            ValidateComponent.validateEmail(request.getEmail());
        }

        Users updatedUser = new Users.Builder()
                .id(existingUser.getId())
                .userName(Optional.of(request.getUserName()).orElse(existingUser.getUserName()))
                .firstName(Optional.of(request.getFirstName()).orElse(existingUser.getFirstName()))
                .lastName(Optional.of(request.getLastName()).orElse(existingUser.getLastName()))
                .roles(request.getIsTrainer() != null
                        ? request.getIsTrainer() == 1 ? Role.TRAINER : Role.USER
                        : existingUser.getRole())
                .email(Optional.of(request.getEmail()).orElse(existingUser.getEmail()))
                .build();

        UserInfo updatedUserInfo = new UserInfo.Builder()
                .id(existinguserInfo.getId())
                .height(Optional.of(request.getHeight()).orElse(existinguserInfo.getHeight()))
                .weight(Optional.of(request.getWeight()).orElse(existinguserInfo.getWeight()))
                .dateOfBirth(existinguserInfo.getDateOfBirth())
                .user(updatedUser)
                .age(existinguserInfo.getAge())
                .goals(request.getGoals() != null
                        ? (Goals) SupportComponent.getEnumValue(Goals.values(), request.getGoals())
                        : existinguserInfo.getGoals())
                .trainingLevel(request.getTrainingLevel() != null
                        ? (TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), request.getTrainingLevel())
                        : existinguserInfo.getTrainingLevel())
                .build();

        userRepository.setUser(updatedUser);
        userInfoRepository.setUserInfo(updatedUserInfo);
        return new Result(Result.Message.SUCCESS);
    }

    public GetUsersResponse getUsers(GetUsersRequest request) {
        List<Users> usersList = request.getUserId() == null
                ? userRepository.getAllUsers()
                : request.getUserId()
                .stream()
                .map(userRepository::getUserById)
                .filter(Objects::nonNull)
                .toList();
        if (usersList.isEmpty()) {
            throw LogicException.of(ResponseCode.NOT_FOUND_USER, request.getUserId());
        }

        return new GetUsersResponse()
                .setUsers(usersList.stream()
                        .map(user -> {
                            UserInfo userInfo = userInfoRepository.getUserInfoByUserId(user.getId());
                            return new GetUsersResponse.UserData.UserDataBuilder()
                                    .userName(user.getUserName())
                                    .firstName(user.getFirstName())
                                    .lastName(user.getLastName())
                                    .gender(user.getGender().getDescription())
                                    .email(user.getEmail())
                                    .dateOfBirth(userInfo.getDateOfBirth())
                                    .age(userInfo.getAge())
                                    .role(user.getRole().getDescription())
                                    .trainingLevel(userInfo.getTrainingLevel().getDescription())
                                    .goals(userInfo.getGoals().getDescription())
                                    .weight(userInfo.getWeight())
                                    .height(userInfo.getHeight())
                                    .build();
                        })
                        .toList());
    }

}
