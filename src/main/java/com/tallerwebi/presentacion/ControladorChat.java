package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ControladorChat {

    @Autowired
    private ServicioChatGPT servicioChatGPT;

    @PostMapping
    public Map<String, String> conversar(@RequestBody Map<String, String> payload) {
        String mensaje = payload.get("mensaje");
        String respuesta = servicioChatGPT.obtenerRespuesta(mensaje);
        return Map.of("respuesta", respuesta);
    }
}