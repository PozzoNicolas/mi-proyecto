package com.tallerwebi.TDD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.ServicioHuggingFaceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class ServicioHuggingFaceTest {

    private HttpClient mockClient;
    private HttpResponse<String> mockResponse;
    private ServicioHuggingFaceImpl servicio;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockClient = Mockito.mock(HttpClient.class);
        mockResponse = Mockito.mock(HttpResponse.class);
        servicio = new ServicioHuggingFaceImpl(mockClient);
    }

    @Test
    void deberiaRetornarRespuestaCorrectaCuandoApiDevuelve200() throws Exception {
        // Se crea la respuesta a enviar. 
        String jsonResponse = mapper.writeValueAsString(Map.of(
            "choices", new Object[]{
                Map.of("message", Map.of("content", "La capital de Francia es París."))
            }
        ));

        // Al procesar correctamente la api, retorna 200
        when(mockResponse.statusCode()).thenReturn(200);
        //Te responde
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockClient.<String>send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        //Verificas que lo recibido coincida con la respuesta falsa
        String resultado = servicio.obtenerRespuesta("¿Cuál es la capital de Francia?");
        assertEquals("La capital de Francia es París.", resultado);
    }

    @Test
    void deberiaManejarErrorDelServidor() throws Exception {
        // 500, error con el servidor de la Ia
        when(mockResponse.statusCode()).thenReturn(500);
        when(mockResponse.body()).thenReturn("Internal Server Error");
        when(mockClient.<String>send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String resultado = servicio.obtenerRespuesta("Hola");
        assertEquals("❌ Error en la conexión con Hugging Face. Código: 500. Respuesta: Internal Server Error", resultado);
    }

    @Test
    void deberiaManejarErrorPorParteDelToken() throws Exception {
        when(mockResponse.statusCode()).thenReturn(401);
        when(mockResponse.body()).thenReturn("Internal Server Error");
        when(mockClient.<String>send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        String resultado = servicio.obtenerRespuesta("Hola");
        assertEquals("❌ Error en la conexión con Hugging Face. Código: 401. Respuesta: Internal Server Error", resultado);

    }

    @Test
    void deberiaManejarExcepcionDeConexion() throws Exception {
        //En caso que tarde mucho la conexion
        when(mockClient.<String>send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Connection timed out"));

        String resultado = servicio.obtenerRespuesta("Hola");
        assertEquals("Error al conectar con Hugging Face: Connection timed out", resultado);
    }
}
