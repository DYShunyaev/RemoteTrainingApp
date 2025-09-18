package d.shunyaev.RemoteTrainingApp.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompletionRequest {

    private String prompt;
    @JsonProperty("max_tokens")
    private int maxTokens;
    private boolean stream;

    public static class Builder {
        private String prompt;
        private int maxTokens;
        private boolean stream;

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public CompletionRequest build() {
            CompletionRequest completionRequest = new CompletionRequest();

            completionRequest.setStream(this.stream);
            completionRequest.setPrompt(this.prompt);
            completionRequest.setMaxTokens(this.maxTokens);

            return completionRequest;
        }
    }

}