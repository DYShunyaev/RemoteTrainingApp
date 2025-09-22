package d.shunyaev.RemoteTrainingApp.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.model.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {

    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @Schema(
            defaultValue = "NON",
            example = "NON",
            allowableValues = {
                    "MAN",
                    "WOMAN",
                    "NON"
            },
            description = "Допустимые значения: MAN, WOMAN, WOMAN",
            nullable = true
    )
    private String email;
    @Schema(
            defaultValue = "0",
            example = "1",
            allowableValues = {
                    "0",
                    "1"
            },
            description = "0 - пользователь,\n" +
                    "1 - тренер",
            nullable = true
    )
    @JsonProperty("is_trainer")
    private Integer isTrainer;
    @Schema(
            defaultValue = "Новичок",
            example = "Новичок",
            allowableValues = {
                    "Новичок",
                    "Средний",
                    "Продвинутый"
            },
            description = "Допустимые значения: Новичок, Средний, Продвинутый",
            nullable = true
    )
    @JsonProperty("training_level")
    private String trainingLevel;
    @Schema(
            defaultValue = "Поддержание формы",
            example = "Поддержание формы",
            allowableValues = {
                    "Поддержание формы",
                    "Похудение",
                    "Набор массы"
            },
            description = "Допустимые значения: Поддержание формы, Похудение, Набор массы",
            nullable = true
    )
    private String goals;
    private Long weight;
    private Long height;

}
