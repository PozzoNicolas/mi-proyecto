package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ControladorChat {

    @Autowired
    private ServicioChatGPT servicioChatGPT;

    @PostMapping
    public String conversar(@RequestBody String mensaje) {
        return servicioChatGPT.obtenerRespuesta(mensaje);
    }
}