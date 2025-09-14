package d.shunyaev.RemoteTrainingApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    // Тест для публичного ресурса - должен быть доступен без авторизации
//    @Test
    void testPublicResourceAccess() throws Exception {
        mockMvc.perform(post("/api/get_users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    // Тест для защищенного ресурса с авторизованным пользователем
//    @WithMockUser(username = "user", roles = "USER")
//    @Test
//    void testSecureResourceWithUser() throws Exception {
//        mockMvc.perform(get("/secure/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    // Тест для защищенного ресурса с неавторизованным пользователем
//    @Test
//    void testSecureResourceWithoutAuth() throws Exception {
//        mockMvc.perform(get("/secure/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//    }

    // Тест для администратора
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    @Test
//    void testAdminResource() throws Exception {
//        mockMvc.perform(get("/secure/admin")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

    // Тест для проверки отказа доступа администратору к пользовательскому ресурсу
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    @Test
//    void testAdminAccessToUserResource() throws Exception {
//        mockMvc.perform(get("/secure/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
