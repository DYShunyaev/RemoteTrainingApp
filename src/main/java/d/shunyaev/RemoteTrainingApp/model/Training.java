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


    public static class Builder {
        private long id;
        private Long userId;
        private DayOfWeek dayOfWeek;
        private LocalDate date;
        private Boolean isDone;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder dayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder isDone(boolean isDone) {
            this.isDone = isDone;
            return this;
        }

        public Training build() {
            Training training = new Training();

            training.setId(this.id);
            training.setUserId(this.userId);
            training.setDayOfWeek(this.dayOfWeek);
            training.setDate(this.date);
            training.setIsDone(this.isDone);

            return training;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) && Objects.equals(userId, training.userId) && dayOfWeek == training.dayOfWeek && Objects.equals(date, training.date) && Objects.equals(isDone, training.isDone) && Objects.equals(muscleGroup, training.muscleGroup);
    }

    public boolean equalsWithIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(userId, training.userId) && dayOfWeek == training.dayOfWeek && Objects.equals(date, training.date) && Objects.equals(isDone, training.isDone)
//                && Objects.equals(muscleGroup, training.muscleGroup)
             ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, dayOfWeek, date, isDone, muscleGroup);
    }
}
