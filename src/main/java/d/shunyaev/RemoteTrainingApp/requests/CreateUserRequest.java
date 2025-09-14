package d.shunyaev.RemoteTrainingApp.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class CreateUserRequest {

    @JsonProperty("user_name")
    @NonNull
    private String userName;
    @JsonProperty("first_name")
    @NonNull
    private String firstName;
    @JsonProperty("last_name")
    @NonNull
    private String lastName;
    @Schema(
            defaultValue = "0",
            example = "1",
            allowableValues = {
                    "0",
                    "1"
            },
            description = "0 - пользователь,\n" +
                    "1 - тренер"
    )
    @JsonProperty("is_trainer")
    private int isTrainer;
    @Schema(
            defaultValue = "NON",
            example = "NON",
            allowableValues = {
                    "MAN",
                    "WOMAN",
                    "NON"
            },
            description = "Допустимые значения: MAN, WOMAN, WOMAN"
    )
    @NonNull
    private String gender;
    private String email;
    @Schema(minProperties = 40)
    private long height; // ограничение на больше 40
    @Schema(minProperties = 40)
    private long weight; // ограничение на больше 40
    @JsonProperty("date_of_birth")
    @NonNull
    private LocalDate dateOfBirth;
    @Schema(
            defaultValue = "Поддержание формы",
            example = "Поддержание формы",
            allowableValues = {
                    "Поддержание формы",
                    "Похудение",
                    "Набор массы"
            },
            description = "Допустимые значения: Поддержание формы, Похудение, Набор массы"
    )
    @NonNull
    private String goals;
    @Schema(
            defaultValue = "Новичок",
            example = "Новичок",
            allowableValues = {
                    "Новичок",
                    "Средний",
                    "Продвинутый"
            },
            description = "Допустимые значения: Новичок, Средний, Продвинутый"
    )
    @JsonProperty("training_level")
    @NonNull
    private String trainingLevel;

}
