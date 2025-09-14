package d.shunyaev.RemoteTrainingApp.components;

import d.shunyaev.RemoteTrainingApp.enums.EnumInterface;
import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class SupportComponent {

    public EnumInterface getEnumValue(EnumInterface[] values, String description) {
        return Arrays.stream(values)
                .filter(e -> description.equals(e.getDescription()))
                .findFirst()
                .orElseThrow(() -> LogicException.of(ResponseCode.INVALID_VALUE));
    }
}
