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
import d.shunyaev.RemoteTrainingApp.requests.users.SetTrainerRequest;
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

    @Transactional
    public Result setTrainer(SetTrainerRequest request) {
        ValidateComponent.notNull(
                request.getUserId(),
                request.getTrainerId()
        );

        if (!userRepository.userIsExist(request.getUserId())
                || !userRepository.userIsExist(request.getTrainerId())) {
            throw LogicException.of(ResponseCode.NOT_FOUND_USER, request.getUserId());
        }
        if (Objects.nonNull(userRepository.getTrainerByUserId(request.getUserId()))) {
            throw LogicException.of(ResponseCode.USER_HAVE_A_TRAINER, request.getUserId());
        }
        Users trainer = userRepository.getUserById(request.getTrainerId());
        if (trainer.getRole().equals(Role.USER)) {
            throw LogicException.of(ResponseCode.USER_NOT_TRAINER, trainer.getUserName());
        }

        userRepository.setTrainer(request.getUserId(), request.getTrainerId());
        return new Result(Result.Message.SUCCESS);
    }

    public Result updateUser(UpdateUserRequest request) {
        ValidateComponent.notNull(
                request.getUserId()
        );

        if (!userRepository.userIsExist(request.getUserId())) {
            throw LogicException.of(ResponseCode.NOT_FOUND_USER, request.getUserId());
        }

        Users existingUser = userRepository.getUserById(request.getUserId());
        ValidateComponent.userNotNull(existingUser);

        UserInfo existinguserInfo = userInfoRepository.getUserInfoByUserName(request.getUserName());

        if (request.getEmail() != null) {
            ValidateComponent.validateEmail(request.getEmail());
        }

        Users updatedUser = new Users()
                .setId(existingUser.getId())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setRole(Objects.nonNull(request.getIsTrainer())
                        ? request.getIsTrainer() == 1 ? Role.TRAINER : Role.USER
                        : null)
                .setEmail(request.getEmail());

        UserInfo updatedUserInfo = new UserInfo()
                .setId(existinguserInfo.getId())
                .setHeight(request.getHeight())
                .setWeight(request.getWeight())
                .setGoals(Objects.nonNull(request.getGoals())
                        ? (Goals) SupportComponent.getEnumValue(Goals.values(), request.getGoals())
                        : null)
                .setTrainingLevel(Objects.nonNull(request.getTrainingLevel())
                        ? (TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), request.getTrainingLevel())
                        : null);

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
                            return new GetUsersResponse.UserData()
                                    .setUserId(user.getId())
                                    .setUserName(user.getUserName())
                                    .setFirstName(user.getFirstName())
                                    .setLastName(user.getLastName())
                                    .setGender(user.getGender().getDescription())
                                    .setEmail(user.getEmail())
                                    .setDateOfBirth(userInfo.getDateOfBirth())
                                    .setAge(userInfo.getAge())
                                    .setRole(user.getRole().getDescription())
                                    .setTrainingLevel(userInfo.getTrainingLevel().getDescription())
                                    .setGoals(userInfo.getGoals().getDescription())
                                    .setWeight(userInfo.getWeight())
                                    .setHeight(userInfo.getHeight());
                        })
                        .toList());
    }

}
