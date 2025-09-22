package d.shunyaev.RemoteTrainingApp.controllers;

import d.shunyaev.RemoteTrainingApp.model.Result;
import d.shunyaev.RemoteTrainingApp.requests.users.CreateUserRequest;
import d.shunyaev.RemoteTrainingApp.requests.users.UpdateUserRequest;
import d.shunyaev.RemoteTrainingApp.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import org.core.containers.RequestContainer;
import org.core.containers.ResponseContainer;
import org.core.controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@RestController
public class UserController extends AbstractController {

    private static final String SET_USER = API_PREFIX + "/user";
    private static final String UPDATE_USER = API_PREFIX + "/update_user";

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(summary = "Сервис создания пользователя")
    @PostMapping(SET_USER)
    public ResponseContainer<Result> createUser(@NotNull @RequestBody RequestContainer<CreateUserRequest> requestContainer) {
        return call(userServices::setNewUser, requestContainer);
    }

    @Operation(summary = "Сервис обновления данных пользователя")
    @PostMapping(UPDATE_USER)
    public ResponseContainer<Result> updateUser(@NotNull @RequestBody RequestContainer<UpdateUserRequest> requestContainer) {
        return call(userServices::updateUser, requestContainer);
    }

}