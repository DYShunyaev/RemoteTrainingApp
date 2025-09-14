package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.enums.Goals;
import d.shunyaev.RemoteTrainingApp.enums.TrainingLevel;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import d.shunyaev.RemoteTrainingApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserInfo> getAllUserInfo() {
        String sql = "SELECT * from user_info";
        return jdbcTemplate.query(sql, mapToRowToUser());
    }

    public UserInfo getUserInfoById(long id) {
        String sql = "SELECT * from user_info where id = ?";
        return jdbcTemplate.queryForObject(sql, UserInfo.class, id);
    }

    public UserInfo getUserInfoByUserId(long id) {
        String sql = "SELECT * from user_info where user_id = ?";
        return jdbcTemplate.queryForObject(sql, UserInfo.class, id);
    }

    public void setUserInfo(UserInfo userInfo) {
        String sql = "insert into user_info(weight, height, date_of_birth, user_id, age, goals, training_level) " +
                "VALUES (?, ?, ?, (select id from users\n" +
                "where user_name = ?), ?, ?, ?)";
        jdbcTemplate.update(sql,
                userInfo.getWeight(),
                userInfo.getHeight(),
                userInfo.getDateOfBirth(),
                userInfo.getUser().getUserName(),
                userInfo.getAge(),
                userInfo.getGoals().getDescription(),
                userInfo.getTrainingLevel().getDescription());
    }

    private RowMapper<UserInfo> mapToRowToUser() {
        return ((rs, rowNum) -> new UserInfo.Builder()
                .height(rs.getInt("height"))
                .weight(rs.getInt("weight"))
                .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                .user(new Users())
                .age(rs.getInt("age"))
                .goals((Goals) SupportComponent.getEnumValue(Goals.values(), rs.getString("goals")))
                .trainingLevel((TrainingLevel) SupportComponent.getEnumValue(TrainingLevel.values(), rs.getString("training_level")))
                .build());
    }

}
