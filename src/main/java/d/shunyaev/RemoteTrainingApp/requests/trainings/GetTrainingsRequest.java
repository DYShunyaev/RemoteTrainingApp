package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTrainingsRequest {

    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("date_of_training")
    @Schema(defaultValue = "2025-09-21",
            description = "Дата тренировки",
            nullable = true)
    private LocalDate dateOfTraining;
    @JsonProperty("day_of_week")
    @Schema(defaultValue = "Понедельник",
            description = "День тренировки",
            nullable = true)
    private String dayOfWeek;
}
