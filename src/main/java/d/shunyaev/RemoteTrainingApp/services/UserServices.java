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
import d.shunyaev.RemoteTrainingApp.requests.users.CreateUserRequest;
import d.shunyaev.RemoteTrainingApp.requests.users.GetUsersRequest;
import d.shunyaev.RemoteTrainingApp.requests.users.UpdateUserRequest;
import d.shunyaev.RemoteTrainingApp.responses.GetUsersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        UserInfo userInfo = SetNewUserComponent.createUserInfo(createUserRequest, currentDay, user.getUserName());

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

        UserInfo existinguserInfo = userInfoRepository.getUserInfoByUserName(request.getUserName());

        if (request.getEmail() != null) {
            ValidateComponent.validateEmail(request.getEmail());
        }

        Users updatedUser = new Users.Builder()
                .id(existingUser.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .roles(Objects.nonNull(request.getIsTrainer())
                        ? request.getIsTrainer() == 1 ? Role.TRAINER : Role.USER
                        : null)
                .email(request.getEmail())
                .build();

        UserInfo updatedUserInfo = new UserInfo.Builder()
                .id(existinguserInfo.getId())
                .height(request.getHeight())
                .weight(request.getWeight())
                .goals(Objects.nonNull(request.getGoals())
                        ? (Goals) SupportComponent.getEnumValue(Goals.values(), request.getGoals())
                        : null)
                .trainingLevel(Objects.nonNull(request.getTrainingLevel())
                        ? (TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), request.getTrainingLevel())
                        : null)
                .build();

        userRepository.updateUser(updatedUser);
        userInfoRepository.updateUserInfo(updatedUserInfo);
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
                            UserInfo userInfo = userInfoRepository.getUserInfoByUserName(user.getUserName());
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
