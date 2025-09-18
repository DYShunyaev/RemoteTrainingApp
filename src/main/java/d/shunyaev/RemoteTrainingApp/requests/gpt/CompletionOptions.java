package d.shunyaev.RemoteTrainingApp.requests.gpt;

import lombok.Data;

@Data
public class CompletionOptions {

    private boolean stream;
    private double temperature;
    private int maxTokens;

    public static class Builder {
        private boolean stream;
        private double temperature;
        private int maxTokens;

        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public CompletionOptions build() {
            CompletionOptions completionOptions = new CompletionOptions();

            completionOptions.setStream(this.stream);
            completionOptions.setTemperature(this.temperature);
            completionOptions.setMaxTokens(this.maxTokens);

            return completionOptions;
        }
    }
}
