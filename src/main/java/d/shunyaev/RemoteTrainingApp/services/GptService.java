package d.shunyaev.RemoteTrainingApp.services;

import d.shunyaev.RemoteTrainingApp.requests.CompletionRequest;
import d.shunyaev.RemoteTrainingApp.requests.gpt.CompletionOptions;
import d.shunyaev.RemoteTrainingApp.requests.gpt.GptRequest;
import d.shunyaev.RemoteTrainingApp.requests.gpt.Message;
import d.shunyaev.RemoteTrainingApp.responses.CompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GptService extends AbstractService {

    private final RestTemplate restTemplate;
    @Value("${gpt.url}")
    private String url;
    @Value("${gpt.key}")
    private String key;
    @Value("${gpt.model}")
    private String modelUri;

    @Autowired
    public GptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendCompletionRequest(CompletionRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key %s".formatted(key));
        headers.set("Content-Type", "application/json");

        GptRequest requestToGpt = new GptRequest.Builder()
                .modelUri(modelUri)
                .completionOptions(
                        new CompletionOptions.Builder()
                                .maxTokens(request.getMaxTokens())
                                .temperature(0.6)
                                .stream(false)
                                .build()
                )
                .messages(List.of(
                        new Message.Builder()
                                .role("system")
                                .text(request.getPrompt())
                                .build()
                ))
                .build();

        HttpEntity<GptRequest> entity = new HttpEntity<>(requestToGpt, headers);

        // Отправляем POST-запрос
        ResponseEntity<CompletionResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, CompletionResponse.class);

        // Получаем ответ
        return response.getBody().getResult().getAlternatives().get(0).getMessage().getText();
    }
}