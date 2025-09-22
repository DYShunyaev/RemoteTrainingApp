package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class GetTrainingsResponse {
    List<Trainings> trainings;

    @Data
    @Accessors(chain = true)
    public static class Trainings {
        @JsonProperty("day_of_week")
        private String dayOfWeek;
        private LocalDate date;
        @JsonProperty("is_done")
        private Boolean isDone;
        List<Exercises> exercises;
    }

    @Data
    @Accessors(chain = true)
    public static class Exercises {
        @JsonProperty("exercise_name")
        private String exerciseName;
        private int quantity;
        private int approach;
    }
}
