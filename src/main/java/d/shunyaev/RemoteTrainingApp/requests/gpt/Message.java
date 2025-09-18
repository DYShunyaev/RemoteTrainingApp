package d.shunyaev.RemoteTrainingApp.requests.gpt;

import lombok.Data;

@Data
public class Message {

    private String role;
    private String text;

    public static class Builder {

        private String role;
        private String text;

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Message build() {
            Message message = new Message();

            message.setRole(this.role);
            message.setText(this.text);

            return message;
        }
    }
}
