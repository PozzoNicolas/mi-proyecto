package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioHuggingFace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ControladorChat {

    @Autowired
    private ServicioHuggingFace servicioHuggingFace;

    @PostMapping
    public Map<String, String> conversar(@RequestBody Map<String, String> payload) {
        String mensaje = payload.get("mensaje");
        System.out.println(mensaje);
        String respuesta = servicioHuggingFace.obtenerRespuesta(mensaje);
        System.out.println(respuesta);
        return Map.of("respuesta", respuesta);
    }


}