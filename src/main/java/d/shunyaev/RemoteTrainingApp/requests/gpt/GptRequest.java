package d.shunyaev.RemoteTrainingApp.requests.gpt;

import lombok.Data;

import java.util.List;

@Data
public class GptRequest {

    private String modelUri;
    private CompletionOptions completionOptions;
    private List<Message> messages;

    public static class Builder {
        private String modelUri;
        private CompletionOptions completionOptions;
        private List<Message> messages;

        public Builder modelUri(String modelUri) {
            this.modelUri = "gpt://%s/yandexgpt-lite".formatted(modelUri);
            return this;
        }

        public Builder completionOptions(CompletionOptions completionOptions) {
            this.completionOptions = completionOptions;
            return this;
        }

        public Builder messages (List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public GptRequest build() {
            GptRequest gptRequest = new GptRequest();

            gptRequest.setModelUri(modelUri);
            gptRequest.setMessages(this.messages);
            gptRequest.setCompletionOptions(this.completionOptions);

            return gptRequest;
        }
    }
}
