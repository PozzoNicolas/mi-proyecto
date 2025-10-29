package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServicioChatGPTImpl implements ServicioChatGPT {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    public String obtenerRespuesta(String mensajeUsuario) {
        try {
            // 1️⃣ Configurar cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // 2️⃣ Crear cuerpo JSON para la API de OpenAI
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-3.5-turbo");
            body.put("messages", new Object[]{
                    Map.of("role", "system", "content", "Sos un asistente veterinario amable y útil llamado VetGPT."),
                    Map.of("role", "user", "content", mensajeUsuario)
            });

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(body);

            // 3️⃣ Crear solicitud POST a la API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            // 4️⃣ Enviar solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5️⃣ Procesar respuesta
            if (response.statusCode() == 200) {
                Map<?, ?> json = mapper.readValue(response.body(), Map.class);
                var choices = (java.util.List<?>) json.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<?, ?> choice = (Map<?, ?>) choices.get(0);
                    Map<?, ?> message = (Map<?, ?>) choice.get("message");
                    return message.get("content").toString().trim();
                }
            }

            return "❌ Error en la conexión con OpenAI. Código: " + response.statusCode() +
                    ". Respuesta: " + response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al conectar con el servicio de OpenAI: " + e.getMessage();
        }
    }
}
