package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.enums.Gender;
import d.shunyaev.RemoteTrainingApp.enums.Role;
import d.shunyaev.RemoteTrainingApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Users> getAllUsers() {
        String sql = "SELECT * from users";
        return jdbcTemplate.query(sql, mapToRowToUser());
    }

    public Users getUserById(long id) {
        String sql = "SELECT * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, Users.class, id);
    }
    public boolean isUniqueUserName(String userName) {
        String sql = "select count(user_name) from users\n" +
                "where user_name = '%s'".formatted(userName);
        return jdbcTemplate.queryForObject(sql, Integer.class) == 0;
    }

    public void setUser(Users user) {
        String sql = "insert into users (email, first_name, gender, last_name, role, user_name) VALUES" +
                "(?, ?,?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getFirstName(),
                user.getGender().getDescription(),
                user.getLastName(),
                user.getRole().getDescription(),
                user.getUserName());
    }

    private RowMapper<Users> mapToRowToUser() {
        return ((rs, rowNum) -> new Users.Builder()
                .id(rs.getLong("id"))
                .userName(rs.getString("user_name"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .roles((Role) SupportComponent.getEnumValue(Role.values(), rs.getString("role")))
                .gender((Gender) SupportComponent.getEnumValue(Gender.values(), rs.getString("gender")))
                .email(rs.getString("email"))
                .build());
    }
}
