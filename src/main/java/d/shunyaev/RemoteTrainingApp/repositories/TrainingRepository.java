package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public class TrainingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrainingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNewTraining(Training training) {
        String sql = """
                insert into training (user_id, day_of_week, training_date, is_done)
                values (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                training.getUserId(),
                SupportComponent.dayOfWeekToRus(training.getDayOfWeek()),
                training.getDate(),
                false);
    }

    public List<Training> getTrainingsByUserId(long userId) {
        String sql = """
                select * from training
                where user_id = ?
                """;
        return jdbcTemplate.query(sql, mapToRowToTraining(), userId);
    }

    public Training getTrainingById(long id) {
        String sql = """
                select * from training
                where id = ?
                """;
        return jdbcTemplate.query(sql, mapToRowToTraining(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private RowMapper<Training> mapToRowToTraining() {
        return ((rs, rowNum) -> new Training()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setDate(rs.getDate("training_date").toLocalDate())
                .setDayOfWeek(SupportComponent.compareRusToDayOfWeek(rs.getString("day_of_week")))
                .setIsDone(rs.getBoolean("is_done")));
    }
}
