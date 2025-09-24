package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.enums.Gender;
import d.shunyaev.RemoteTrainingApp.enums.Role;
import d.shunyaev.RemoteTrainingApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public boolean userIsExist(long userId) {
        String sql = """
                select count(*) from users where id = ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, userId) == 1;
    }

    public Users getUserById(long id) {
        String sql = "SELECT * from users where id = ?";
        return jdbcTemplate.query(sql, mapToRowToUser(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Users getUserByUserName(String userName) {
        String sql = "SELECT * from users where user_name = ?";
        return jdbcTemplate.query(sql, mapToRowToUser(), userName)
                .stream()
                .findFirst()
                .orElse(null);
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

    public void updateUser(Users user) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<Object> params = new ArrayList<>();

        if (Objects.nonNull(user.getFirstName())) {
            sql.append("first_name = ?, ");
            params.add(user.getFirstName());
        }
        if (Objects.nonNull(user.getLastName())) {
            sql.append("last_name = ?, ");
            params.add(user.getLastName());
        }
        if (Objects.nonNull(user.getRole())) {
            sql.append("role = ?, ");
            params.add(user.getRole().getDescription());
        }
        if (Objects.nonNull(user.getEmail())) {
            sql.append("email = ?, ");
            params.add(user.getEmail());
        }

        if (params.isEmpty()) return; // Если нет обновляемых полей, выходим

        sql.replace(sql.length() - 2, sql.length(), " "); // Убираем последнюю запятую и пробел
        sql.append(" WHERE id = ?");
        params.add(user.getId());

        // Выполняем обновление с параметрами
        jdbcTemplate.update(sql.toString(), params.toArray());
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
