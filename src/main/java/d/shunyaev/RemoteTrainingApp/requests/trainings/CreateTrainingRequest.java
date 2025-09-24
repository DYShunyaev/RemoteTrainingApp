package d.shunyaev.RemoteTrainingApp.requests.trainings;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTrainingRequest {

    @JsonProperty("user_id")
    private Long userId;
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
    @JsonProperty("day_of_week")
    private String dayOfWeek;
    @JsonProperty("muscle_group")
    @Schema(
            defaultValue = "Грудные мышцы",
            description = "Группа мышц"
    )
    private String muscleGroup;
    @Schema(defaultValue = "2025-09-22",
            description = "Дата тренировки")
    private LocalDate date;
}
