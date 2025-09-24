package d.shunyaev.RemoteTrainingApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Exercise {

    private long id;
    private long trainingId;
    private String exerciseName;
    private int quantity; //количество
    private int approach; //подходы

}
