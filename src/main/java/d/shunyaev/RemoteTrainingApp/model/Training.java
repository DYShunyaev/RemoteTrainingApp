package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Accessors(chain = true)
public class Training {

    private Long id;
    private Long userId;
    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private Boolean isDone;
    private String muscleGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) && Objects.equals(userId, training.userId)
                && dayOfWeek == training.dayOfWeek && Objects.equals(date, training.date) && Objects.equals(isDone, training.isDone) && Objects.equals(muscleGroup, training.muscleGroup);
    }

    public boolean equalsWithIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(userId, training.userId) && dayOfWeek == training.dayOfWeek
                && Objects.equals(date, training.date) && Objects.equals(isDone, training.isDone)
                && Objects.equals(muscleGroup, training.muscleGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, dayOfWeek, date, isDone, muscleGroup);
    }
}
