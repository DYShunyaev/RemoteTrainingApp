package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.components.SupportComponent;
import d.shunyaev.RemoteTrainingApp.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TrainingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrainingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNewTraining(Training training) {
        String sql = """
                insert into training (user_id, day_of_week, training_date, is_done, muscle_group)
                values (?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                training.getUserId(),
                SupportComponent.dayOfWeekToRus(training.getDayOfWeek()),
                training.getDate(),
                false,
                training.getMuscleGroup());
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

    public void updateTraining(Training training) {
        StringBuilder sql = new StringBuilder("UPDATE training SET ");
        List<Object> params = new ArrayList<>();

        if (Objects.nonNull(training.getDayOfWeek())) {
            sql.append("day_of_week = ?, ");
            params.add(training.getDayOfWeek());
        }
        if (Objects.nonNull(training.getDate())) {
            sql.append("training_date = ?, ");
            params.add(training.getDate());
        }
        if (Objects.nonNull(training.getMuscleGroup())) {
            sql.append("muscle_group = ?, ");
            params.add(training.getMuscleGroup());
        }
        if (Objects.nonNull(training.getIsDone())) {
            sql.append("is_done = ?, ");
            params.add(training.getIsDone());
        }

        if (params.isEmpty()) return; // Если нет обновляемых полей, выходим

        sql.replace(sql.length() - 2, sql.length(), " "); // Убираем последнюю запятую и пробел
        sql.append(" WHERE id = ?");
        params.add(training.getId());

        // Выполняем обновление с параметрами
        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    private RowMapper<Training> mapToRowToTraining() {
        return ((rs, rowNum) -> new Training()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setDate(rs.getDate("training_date").toLocalDate())
                .setDayOfWeek(SupportComponent.compareRusToDayOfWeek(rs.getString("day_of_week")))
                .setIsDone(rs.getBoolean("is_done"))
                .setMuscleGroup(rs.getString("muscle_group")));
    }
}
