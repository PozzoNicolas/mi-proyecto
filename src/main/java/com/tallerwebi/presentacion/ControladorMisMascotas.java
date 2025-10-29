package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMisMascotas {

    private final ServicioUsuario servicioUsuario;

    public ControladorMisMascotas(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(Model model) {
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(100L); // por el momento usamos el usuario con ID 100

        List<Mascota> mascotas;
        if (usuario != null) {
            mascotas = usuario.getMascotas();
        } else {
            mascotas = new ArrayList<>();
        }
        model.addAttribute("mascotas", mascotas);
        return "mis-mascotas";
    }
}