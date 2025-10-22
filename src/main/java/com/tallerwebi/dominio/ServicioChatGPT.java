package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.openai.OpenAI;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatCompletionMessage;
import com.openai.models.ChatCompletionMessageRole;

@Service
public class ServicioChatGPT {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public String enviarMensaje(String mensajeUsuario) {
        OpenAI client = new OpenAI(apiKey);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-3.5-turbo")
                .messages(
                        ChatCompletionMessage.builder()
                                .role(ChatCompletionMessageRole.USER)
                                .content(mensajeUsuario)
                                .build()
                )
                .maxTokens(200)
                .temperature(0.7)
                .build();

        return client.chatCompletions().create(params)
                .getChoices().get(0).getMessage().getContent();
    }
}
