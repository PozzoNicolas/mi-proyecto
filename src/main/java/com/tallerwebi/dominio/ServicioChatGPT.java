package com.tallerwebi.dominio;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioChatGPT {


    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    private final OpenAiService openAiService;

    public ServicioChatGPT() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        this.openAiService = new OpenAiService(apiKey);
    }

    public String obtenerRespuesta(String preguntaUsuario) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", "Sos un asistente especializado en salud animal y veterinaria."),
                        new ChatMessage("user", preguntaUsuario)
                ))
                .maxTokens(200)
                .temperature(0.8)
                .build();

        ChatCompletionResult resultado = openAiService.createChatCompletion(request);
        return resultado.getChoices().get(0).getMessage().getContent();
    }
}