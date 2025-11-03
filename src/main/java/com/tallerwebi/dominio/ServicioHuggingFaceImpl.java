package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioHuggingFaceImpl implements ServicioHuggingFace {

    // Use environment variable for API key
    private static final String API_KEY = System.getenv("VET_IA");

    // Hugging Face Inference API endpoint (public model)
    private static final String API_URL = "https://router.huggingface.co/v1/chat/completions";

     private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ServicioHuggingFaceImpl() {
        this(HttpClient.newHttpClient());
    }

    public ServicioHuggingFaceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String obtenerRespuesta(String mensajeUsuario) {
        try {
            // Build router-style JSON payload
            Map<String, Object> body = new HashMap<>();
            body.put("model", "MiniMaxAI/MiniMax-M2:novita"); // replace with your desired model

            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", "Sos un asistente veterinario amable llamado VetGPT."),
                    Map.of("role", "user", "content", mensajeUsuario)
            );

            body.put("messages", messages);

            String requestBody = mapper.writeValueAsString(body);

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse response
            if (response.statusCode() == 200) {
                Map<String, Object> json = mapper.readValue(response.body(), Map.class);
                List<Map<String, Object>> choices = (List<Map<String, Object>>) json.get("choices");

                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return message.get("content").toString().trim();
                } else {
                    return "❌ No se encontró una respuesta válida en la API.";
                }
            } else {
                return "❌ Error en la conexión con Hugging Face. Código: "
                        + response.statusCode() + ". Respuesta: " + response.body();
            }

        } catch (Exception e) {
            return "Error al conectar con Hugging Face: " + e.getMessage();
        }
    }
}