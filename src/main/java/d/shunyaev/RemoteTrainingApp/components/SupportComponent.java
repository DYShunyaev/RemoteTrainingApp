package d.shunyaev.RemoteTrainingApp.components;

import d.shunyaev.RemoteTrainingApp.enums.EnumInterface;
import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.util.Arrays;

@UtilityClass
public class SupportComponent {

    public EnumInterface getEnumValue(EnumInterface[] values, String description) {
        return Arrays.stream(values)
                .filter(e -> description.equals(e.getDescription()))
                .findFirst()
                .orElseThrow(() -> LogicException.of(ResponseCode.INVALID_VALUE));
    }

    public DayOfWeek compareRusToDayOfWeek(String dayOfWeekString) {
        DayOfWeek dayOfWeek;
        switch (dayOfWeekString) {
            case "Понедельник" -> dayOfWeek = DayOfWeek.MONDAY;
            case "Вторник" -> dayOfWeek = DayOfWeek.TUESDAY;
            case "Среда" -> dayOfWeek = DayOfWeek.WEDNESDAY;
            case "Четверг" -> dayOfWeek = DayOfWeek.THURSDAY;
            case "Пятница" -> dayOfWeek = DayOfWeek.FRIDAY;
            case "Суббота" -> dayOfWeek = DayOfWeek.SATURDAY;
            case "Воскресенье" -> dayOfWeek = DayOfWeek.SUNDAY;
            default -> dayOfWeek = null;
        }
        return dayOfWeek;
    }

    public String dayOfWeekToRus(DayOfWeek dayOfWeek) {
        String dayOfWeekString;
        switch (dayOfWeek) {
            case MONDAY -> dayOfWeekString = "Понедельник";
            case TUESDAY -> dayOfWeekString = "Вторник";
            case WEDNESDAY -> dayOfWeekString = "Среда";
            case THURSDAY -> dayOfWeekString = "Четверг";
            case FRIDAY -> dayOfWeekString = "Пятница";
            case SATURDAY -> dayOfWeekString = "Суббота";
            case SUNDAY -> dayOfWeekString = "Воскресенье";
            default -> dayOfWeekString = null;
        }
        return dayOfWeekString;
    }
}
