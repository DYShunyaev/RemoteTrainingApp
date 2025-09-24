package d.shunyaev.RemoteTrainingApp.requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompletionRequest {

    private String prompt;
    private int maxTokens;
    private boolean stream;
}