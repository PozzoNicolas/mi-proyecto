package com.tallerwebi.dominio;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioChatGPT {

    final OpenAiService openAiService = null;

    public default String obtenerRespuesta(String mensajeUsuario) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", "Sos un asistente veterinario amigable, empático y profesional llamado VetGPT. Ayudás a los usuarios a cuidar a sus mascotas, brindando información de salud, vacunas y bienestar animal."),
                        new ChatMessage("user", mensajeUsuario)
                ))
                .maxTokens(250)
                .temperature(0.8)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent();
    }
}