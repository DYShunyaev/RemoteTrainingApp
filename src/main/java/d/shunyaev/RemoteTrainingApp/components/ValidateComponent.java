package d.shunyaev.RemoteTrainingApp.components;

import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import d.shunyaev.RemoteTrainingApp.model.Users;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class ValidateComponent {

    public void notNull(Object... validateObjects) {
        Arrays.stream(validateObjects)
                .forEach(validateObject -> {
                    if (Objects.isNull(validateObject)) {
                        throw LogicException.of(ResponseCode.NPE,"");
                    }
                });
    }

    public void objectMoreZero(Number object) {
        if (object.doubleValue() <= 0) {
            throw LogicException.of(ResponseCode.VALUE_IS_LESS_OR_EQUALS_ZERO);
        }
    }

    public void userNotNull(Users users) {
        if (Objects.isNull(users)) {
            throw LogicException.of(ResponseCode.USER_NOT_FOUND);
        }
    }

    public void defaultValue(Object... validateObjects) {
        Arrays.stream(validateObjects).forEach(object -> {
            if (object instanceof String str && str.equalsIgnoreCase("string") ||
                    object instanceof LocalDate date && date.equals(LocalDate.now())) {
                throw LogicException.of(ResponseCode.DEFAULT_VALUE, object);
            }
        });
    }

    public void dayOfWeekValidation(String dayOfWeek) {
        Stream.of(
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота",
                "Воскресенье"
        )
                .filter(validDay -> validDay.equals(dayOfWeek))
                .findFirst()
                .orElseThrow(() -> LogicException.of(ResponseCode.NOT_VALID_DAY_OF_WEEK, dayOfWeek));

    }

    public boolean assertTwoObject(Object firstObject, Object secondObject) {
        return firstObject.equals(secondObject);
    }

    public void validateEmail(String email) {
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")) {
            throw LogicException.of(ResponseCode.NOT_VALID_EMAIL);
        }
    }

    public void validateBirthDate(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if (birthDate.isAfter(currentDate)) {
            throw LogicException.of(ResponseCode.BIRTH_DATE);
        }
    }

    public void validateDate(LocalDate date) {
        if (!date.equals(LocalDate.now()) && date.isBefore(LocalDate.now())) {
            throw LogicException.of(ResponseCode.DATE_IS_BEFORE_NOW);
        }
    }
}
