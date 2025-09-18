package d.shunyaev.RemoteTrainingApp.responses;

import lombok.Data;

import java.util.List;

@Data
public class CompletionResponse {
    private Result result;
    private String modelVersion;

    @Data
    public static class Result {
        private List<Alternative> alternatives;
        private Usage usage;
    }

    @Data
    public static class Alternative {
        private Message message;
        private String status;
    }

    @Data
    public static class Message {
        private String role;
        private String text;
    }

    @Data
    public static class Usage {
        private int inputTextTokens;
        private int completionTokens;
        private int totalTokens;
        private CompletionTokensDetails completionTokensDetails;
    }

    @Data
    public static class CompletionTokensDetails {
        private int reasoningTokens;
    }

}