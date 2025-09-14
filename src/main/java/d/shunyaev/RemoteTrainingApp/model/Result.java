package d.shunyaev.RemoteTrainingApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "message"})
public class Result {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = 501;

    private final int code;
    @JsonProperty("message")
    private final String message;

    public int getCode() {return this.code;}

    public Result(@JsonProperty("code") int code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Message message) {
        this.code = message.getCode();
        this.message = message.getMessage();
    }

    public static class Message {

        public static final Message SUCCESS = new Message(SUCCESS_CODE, "");
        public static final Message ERROR = new Message(ERROR_CODE, "Ошибка");

        private final int code;
        private final String message;

        public Message(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
