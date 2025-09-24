package d.shunyaev.RemoteTrainingApp.repositories;

import d.shunyaev.RemoteTrainingApp.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExerciseRepository extends BaseRepository {

    @Autowired
    public ExerciseRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void setExercise(Exercise exercise) {
        String sql = """
                insert into exercise (training_id, exercise, quantity, approach)
                values (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                exercise.getTrainingId(),
                exercise.getExerciseName(),
                exercise.getQuantity(),
                exercise.getApproach());
    }

    public List<Exercise> getExercisesByTrainingId(long trainingId) {
        String sql = """
                select * from exercise
                where training_id = ?
                """;
        return jdbcTemplate.query(sql, mapToRowToTraining(), trainingId);
    }

    private RowMapper<Exercise> mapToRowToTraining() {
        return ((rs, rowNum) -> new Exercise()
                .setId(rs.getLong("id"))
                .setTrainingId(rs.getLong("training_id"))
                .setExerciseName(rs.getString("exercise"))
                .setQuantity(rs.getInt("quantity"))
                .setApproach(rs.getInt("approach")));
    }
}
