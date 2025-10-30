package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMisMascotas {

    private final ServicioUsuario servicioUsuario;

    public ControladorMisMascotas(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(Model model, HttpSession session) {

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");

        if (usuarioActual == null) {
            return "redirect:/login";
        }
        List<Mascota> mascotas = usuarioActual.getMascotas();
        model.addAttribute("mascotas", mascotas);
        return "mis-mascotas";


    }
}