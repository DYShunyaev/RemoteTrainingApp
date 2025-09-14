package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise {

    private long id;
    private Training training;
    private String exercise;
    private int quantity; //количество
    private int approach; //подходы

    public static class Builder {

        private long id;
        private Training training;
        private String exercise;
        private int quantity; //количество
        private int approach; //подходы

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder training(Training training) {
            this.training = training;
            return this;
        }

        public Builder exercise(String exercise) {
            this.exercise = exercise;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder approach(int approach) {
            this.approach = approach;
            return this;
        }

        public Exercise build() {
            Exercise exercise = new Exercise();

            exercise.setId(this.id);
            exercise.setTraining(this.training);
            exercise.setExercise(this.exercise);
            exercise.setQuantity(this.quantity);
            exercise.setApproach(this.approach);

            return exercise;
        }
    }

}
