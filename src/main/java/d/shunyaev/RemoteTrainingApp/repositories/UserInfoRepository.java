package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.enums.Goals;
import d.shunyaev.RemoteTrainingApp.enums.TrainingLevel;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserInfoRepository extends BaseRepository {

    @Autowired
    public UserInfoRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<UserInfo> getAllUserInfo() {
        String sql = "SELECT * from user_info";
        return jdbcTemplate.query(sql, mapToRowToUserInfo());
    }

    public UserInfo getUserInfoById(long id) {
        String sql = "SELECT * from user_info where id = ?";
        return jdbcTemplate.queryForObject(sql, UserInfo.class, id);
    }

    public UserInfo getUserInfoByUserName(String userName) {
        String sql = "SELECT * from user_info where user_name = ?";
        return jdbcTemplate.query(sql, mapToRowToUserInfo(), userName)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void setUserInfo(UserInfo userInfo) {
        String sql = "insert into user_info(weight, height, date_of_birth, user_name, age, goals, training_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                userInfo.getWeight(),
                userInfo.getHeight(),
                userInfo.getDateOfBirth(),
                userInfo.getUserName(),
                userInfo.getAge(),
                userInfo.getGoals().getDescription(),
                userInfo.getTrainingLevel().getDescription());
    }

    public void updateUserInfo(UserInfo userInfo) {
        StringBuilder sql = new StringBuilder("UPDATE user_info SET ");
        List<Object> params = new ArrayList<>();

        if (Objects.nonNull(userInfo.getHeight())) {
            sql.append("height = ?, ");
            params.add(userInfo.getHeight());
        }
        if (Objects.nonNull(userInfo.getWeight())) {
            sql.append("weight = ?, ");
            params.add(userInfo.getWeight());
        }
        if (Objects.nonNull(userInfo.getGoals())) {
            sql.append("goals = ?, ");
            params.add(userInfo.getGoals().getDescription());
        }
        if (Objects.nonNull(userInfo.getTrainingLevel())) {
            sql.append("training_level = ?, ");
            params.add(userInfo.getTrainingLevel());
        }

        // Удаляем последнюю запятую и пробел
        if (params.isEmpty()) return; // Если нет обновляемых полей, выходим

        sql.replace(sql.length() - 2, sql.length(), " "); // Убираем последнюю запятую и пробел
        sql.append(" WHERE id = ?");
        params.add(userInfo.getId());

        // Выполняем обновление с параметрами
        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    private RowMapper<UserInfo> mapToRowToUserInfo() {
        return ((rs, rowNum) -> new UserInfo.Builder()
                .height(rs.getLong("height"))
                .weight(rs.getLong("weight"))
                .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                .userName(rs.getString("user_name"))
                .age(rs.getInt("age"))
                .goals((Goals) SupportComponent.getEnumValue(Goals.values(), rs.getString("goals")))
                .trainingLevel((TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), rs.getString("training_level")))
                .build());
    }

}
