package d.shunyaev.RemoteTrainingApp.components;

import d.shunyaev.RemoteTrainingApp.model.Exercise;
import d.shunyaev.RemoteTrainingApp.model.Training;
import d.shunyaev.RemoteTrainingApp.model.UserInfo;
import d.shunyaev.RemoteTrainingApp.model.Users;
import d.shunyaev.RemoteTrainingApp.utils.FileHelper;
import jakarta.json.*;
import lombok.experimental.UtilityClass;

import java.io.StringReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@UtilityClass
public class GptComponent {

    private final List<String> daysOfWeek = List.of(
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
    );

    public Map<Training, List<Exercise>> parseTrainings(String responseGpt, long userId, LocalDate dateFirstTraining) {
        Map<Training, List<Exercise>> trainingsMap = new HashMap<>();

        try (JsonReader jsonReader = Json.createReader(new StringReader(responseGpt.replace("`", "")))) {
            JsonArray trainingsArray = jsonReader.readObject().getJsonArray("trainings");

            for (JsonObject json : trainingsArray.getValuesAs(JsonObject.class)) {
                DayOfWeek dayOfWeek = SupportComponent.compareRusToDayOfWeek(
                        daysOfWeek.stream()
                                .filter(d -> d.equalsIgnoreCase(json.getString("day_of_week")))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Invalid day of week"))
                );

                LocalDate trainingDate = getTrainingDate(dayOfWeek, trainingsMap, dateFirstTraining);

                Training training = new Training()
                        .setUserId(userId)
                        .setMuscleGroup(json.getString("muscle_group"))
                        .setDate(trainingDate)
                        .setIsDone(false)
                        .setDayOfWeek(dayOfWeek);

                List<Exercise> exerciseList = json.getJsonArray("exercises")
                        .getValuesAs(JsonObject.class)
                        .stream()
                        .map(jsonExercise -> new Exercise()
                                .setExerciseName(jsonExercise.getString("exercise_name"))
                                .setApproach(jsonExercise.getInt("approach"))
                                .setQuantity(jsonExercise.getInt("quantity")))
                        .toList();

                trainingsMap.put(training, exerciseList);
            }
        }
        return trainingsMap;
    }

    private LocalDate getTrainingDate(DayOfWeek dayOfWeekActual, Map<Training, List<Exercise>> trainingMap,
                                      LocalDate dateFirstTraining) {
        if (trainingMap.isEmpty()) {
            while (!dateFirstTraining.getDayOfWeek().equals(dayOfWeekActual)) {
                dateFirstTraining = dateFirstTraining.plusDays(1);
            }
            return dateFirstTraining;
        }
        else {
            Training training = trainingMap.keySet()
                    .stream()
                    .max(Comparator.comparing(Training::getDate))
                    .get();
            return training.getDate().plusDays(dayOfWeekActual.getValue() - training.getDayOfWeek().getValue());
        }
    }

    public String getPrompt(int count, Users user, UserInfo userInfo, String dayOfWeekFirstTraining) {
        String prompt = FileHelper.readFileAsString("prompt.txt");

        Map<String, String> replacements = Map.of(
                "count", String.valueOf(count),
                "gender", user.getGender().getDescription(),
                "age", String.valueOf(userInfo.getAge()),
                "height", String.valueOf(userInfo.getHeight()),
                "weight", String.valueOf(userInfo.getWeight()),
                "dayOfWeek", dayOfWeekFirstTraining,
                "trainingLevel", userInfo.getTrainingLevel().getDescription(),
                "goals", userInfo.getGoals().getDescription()
        );

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            prompt = prompt.replaceAll(entry.getKey(), entry.getValue());
        }
        return prompt;
    }
}
