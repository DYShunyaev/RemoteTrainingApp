package d.shunyaev.RemoteTrainingApp.config;

import d.shunyaev.RemoteTrainingApp.enums.ResponseCode;
import d.shunyaev.RemoteTrainingApp.exceptions.LogicException;
import org.core.containers.RequestContainer;
import org.core.containers.ResponseContainer;
import org.core.context.RequestContext;
import org.core.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<ResponseContainer<ErrorResponse>> handleLogicException(LogicException ex) {
        try {
            RequestContainer<?> requestContainer = RequestContext.getCurrentRequest();
            ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());

            ResponseContainer<ErrorResponse> responseContainer = ResponseContainer.of(
                    requestContainer.getTraceId(),
                    requestContainer.getUserInfo(),
                    errorResponse
            );
            return new ResponseEntity<>(responseContainer, HttpStatus.BAD_REQUEST);
        } finally {
            RequestContext.clear();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseContainer<ErrorResponse>> baseException() {
        try {
            ResponseCode exception = ResponseCode.EXCEPTION;
            ResponseContainer<ErrorResponse> responseContainer = getErrorResponse(exception.getCode(), exception.getMessage());

//            RequestContainer<?> requestContainer = RequestContext.getCurrentRequest();
//            ResponseCode exception = ResponseCode.EXCEPTION;
//            ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
//
//            ResponseContainer<ErrorResponse> responseContainer = ResponseContainer.of(
//                    requestContainer.getTraceId(),
//                    requestContainer.getUserInfo(),
//                    errorResponse
//            );
            return new ResponseEntity<>(responseContainer, HttpStatus.BAD_REQUEST);
        } finally {
            RequestContext.clear();
        }
    }

    private ResponseContainer<ErrorResponse> getErrorResponse(int code, String message) {
        RequestContainer<?> requestContainer = RequestContext.getCurrentRequest();
        ErrorResponse errorResponse = new ErrorResponse(code, message);

        return ResponseContainer.of(
                requestContainer.getTraceId(),
                requestContainer.getUserInfo(),
                errorResponse
        );
    }

}
