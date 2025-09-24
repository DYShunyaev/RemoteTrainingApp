package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import d.shunyaev.RemoteTrainingApp.enums.Goals;
import d.shunyaev.RemoteTrainingApp.enums.TrainingLevel;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfo {

    private Long id;
    private Long weight;
    private Long height;
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private String userName;
    private int age;
    private Goals goals;
    @JsonProperty("training_level")
    private TrainingLevel trainingLevel;
}
