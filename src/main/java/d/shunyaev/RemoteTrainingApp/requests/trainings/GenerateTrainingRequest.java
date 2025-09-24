package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GenerateTrainingRequest {

    @JsonProperty("user_id")
    private long userId;
    @Schema(
            defaultValue = "Понедельник",
            example = "Понедельник",
            allowableValues = {
                    "Понедельник",
                    "Вторник",
                    "Среда",
                    "Четверг",
                    "Пятница",
                    "Суббота",
                    "Воскресенье"
            },
            description = "День тренировки"
    )
    @JsonProperty("day_of_week_first_training")
    private String dayOfWeekFirstTraining;
    @Schema(defaultValue = "2025-09-22",
            description = "Дата тренировки")
    @JsonProperty("date_first_training")
    private LocalDate dateFirstTraining;
    @Schema(description = "Количество тренировок")
    private int count;
}
