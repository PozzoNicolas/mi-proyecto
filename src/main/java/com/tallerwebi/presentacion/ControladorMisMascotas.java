package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMisMascotas {

    private final ServicioUsuario servicioUsuario;
    private final ServicioMascota servicioMascota;

    public ControladorMisMascotas(ServicioUsuario servicioUsuario, ServicioMascota servicioMascota) {
        this.servicioUsuario = servicioUsuario;
        this.servicioMascota = servicioMascota;
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(Model model, HttpSession session) {

        Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioActual");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        // 🌟 1. Obtener el ID del usuario de la sesión.
        Long idUsuario = usuarioSesion.getId();

        // 🌟 2. Forzar la recarga del usuario y sus mascotas desde la base de datos.
        // Esto asegura que la lista de mascotas esté sincronizada con la DB.
        Usuario usuarioActualizado = servicioUsuario.buscarUsuarioPorId(idUsuario);

        // Si la lista de mascotas está marcada como Lazy, debes asegurarte que el
        // servicio
        // o el repositorio la carguen (ej: usando FETCH JOIN en el repositorio).
        List<Mascota> mascotas = usuarioActualizado.getMascotas();

        // 🌟 3. Opcional: Reemplazar el objeto en la sesión con el actualizado
        session.setAttribute("usuarioActual", usuarioActualizado);

        model.addAttribute("mascotas", mascotas);
        return "mis-mascotas";

        // Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");

        // if (usuarioActual == null) {
        // return "redirect:/login";
        // }
        // List<Mascota> mascotas = usuarioActual.getMascotas();
        // model.addAttribute("mascotas", mascotas);
        // return "mis-mascotas";

    }

    @PostMapping("/eliminar-mascota")
    public ModelAndView eliminarMascota(@RequestParam("idMascota") Long idMascota) {

        // Si hay un error de JPA, esto lo lanzará y obtendrás un HTTP 500
        servicioMascota.eliminarMascota(idMascota);

        return new ModelAndView("redirect:/mis-mascotas");
        // try {
        // servicioMascota.eliminarMascota(idMascota);

        // // Redirigir a la vista que lista las mascotas
        // return new ModelAndView("redirect:/mis-mascotas");

        // } catch (Exception e) {
        // // Manejar la excepción si ocurre un error en la eliminación (ej: cascada
        // rota)
        // System.err.println("Error al eliminar mascota: " + e.getMessage());
        // return new ModelAndView("redirect:/mis-mascotas?error=eliminacionFallida");
        // }
    }
}