package d.shunyaev.RemoteTrainingApp.controllers;

import d.shunyaev.RemoteTrainingApp.requests.users.GetUserByUserNameRequest;
import d.shunyaev.RemoteTrainingApp.requests.users.GetUsersRequest;
import d.shunyaev.RemoteTrainingApp.responses.GetUsersResponse;
import d.shunyaev.RemoteTrainingApp.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import org.core.containers.RequestContainer;
import org.core.containers.ResponseContainer;
import org.core.controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotNull;

@RestController
public class UserInfoController extends AbstractController {

    private static final String GET_USER = API_PREFIX + "/get_users";
    private static final String GET_USER_BY_USERNAME = API_PREFIX + "/get_users_by_username";
    private final UserServices userServices;

    @Autowired
    public UserInfoController(UserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(summary = "Сервис получения информации о пользователях")
    @PostMapping(GET_USER)
    public ResponseContainer<GetUsersResponse> getUsers(@NotNull @RequestBody RequestContainer<GetUsersRequest> requestContainer) {
        return call(userServices::getUsers, requestContainer);
    }

    @Operation(summary = "Сервис получения информации о пользователях")
    @PostMapping(GET_USER_BY_USERNAME)
    public ResponseContainer<GetUsersResponse> getUsersByUserName(@NotNull @RequestBody RequestContainer<GetUserByUserNameRequest> requestContainer) {
        return call(userServices::getUsers, requestContainer);
    }
}
