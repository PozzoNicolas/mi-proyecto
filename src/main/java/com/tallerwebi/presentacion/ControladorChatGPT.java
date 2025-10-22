package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioChatGPT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
public class ControladorChatGPT {

    private final ServicioChatGPT ServicioChatGPT;

    public ControladorChatGPT(ServicioChatGPT servicioChatGPT) {
        ServicioChatGPT = servicioChatGPT;
    }

    @PostMapping
    @ResponseBody
    public String procesarMensaje(@RequestParam("mensaje") String mensaje) {
        return ServicioChatGPT.enviarMensaje(mensaje);
    }




}
