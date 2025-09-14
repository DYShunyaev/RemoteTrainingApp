package d.shunyaev.RemoteTrainingApp.exceptions;

import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import org.core.errors.AbstractErrorCodeException;

public class LogicException extends AbstractErrorCodeException {

    public LogicException(int code, String message) {
        super(code, message);
    }

    public static LogicException of(ResponseCode code, Object... params) {
        return new LogicException(code.getCode(), String.format(code.getMessage(), params));
    }
}
