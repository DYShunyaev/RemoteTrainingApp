package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Training {

    private long id;
    private Users user;
    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private Boolean isDone;

    public static class Builder {
        private long id;
        private Users user;
        private DayOfWeek dayOfWeek;
        private LocalDate date;
        private Boolean isDone;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder user(Users user) {
            this.user = user;
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
            training.setUser(this.user);
            training.setDayOfWeek(this.dayOfWeek);
            training.setDate(this.date);
            training.setIsDone(this.isDone);

            return training;
        }
    }

}
