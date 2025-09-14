package d.shunyaev.RemoteTrainingApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    OK(0, ""),
    NOT_VALID_EMAIL(-1, "Невалидный email"),
    NPE(-2, "Объект %s содержит пустое значение"),
    DEFAULT_VALUE(-3, "Передано дефолтное значение - %s"),
    NOT_FOUND_USER(-4, "Пользователь с id %s не найден"),
    BIRTH_DATE(-5, "Дата рождения не может быть позднее текущей даты"),
    INVALID_VALUE(-6,"Недопустимое значение"),
    USER_NOT_FOUND(-7,"Пользователь не найден"),
    USER_NAME_NOT_UNIQUE(-8, "Пользователь с user_name: %s уже существует"),
    UNAUTHORIZED(-401, "Ошибка авторизации"),
    EXCEPTION(-500, "Внутренняя ошибка");

    private final int code;
    private final String message;
}
