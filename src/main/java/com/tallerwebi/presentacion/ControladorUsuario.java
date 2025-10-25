package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    // ================================
    // PERFIL DEL USUARIO LOGUEADO
    // ================================
    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView mostrarPerfil(HttpSession session) {
        ModelMap model = new ModelMap();

        // Obtenemos los datos de sesión cargados en el login
        String email = (String) session.getAttribute("EMAIL");

        if (email == null) {
            // Si no hay sesión activa, redirige al login
            model.put("error", "Debes iniciar sesión para ver tu perfil.");
            return new ModelAndView("redirect:/login", model);
        }

        // Buscamos al usuario por su email
        Usuario usuario = servicioUsuario.obtenerPorEmail(email);
        if (usuario == null) {
            model.put("error", "No se encontró la información del usuario.");
            return new ModelAndView("redirect:/login", model);
        }

        // Enviamos los datos al perfil
        model.put("nombre", usuario.getNombreUsuario());
        model.put("email", usuario.getEmail());
        model.put("rol", usuario.getRol());

        return new ModelAndView("perfil", model);
    }

    // ================================
    // ACTUALIZAR DATOS DE PERFIL
    // ================================
    @RequestMapping(path = "/actualizar", method = RequestMethod.POST)
    public ModelAndView actualizarPerfil(@ModelAttribute("usuario") Usuario usuario, HttpSession session) {
        ModelMap model = new ModelMap();

        String email = (String) session.getAttribute("EMAIL");
        if (email == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Usuario usuarioExistente = servicioUsuario.obtenerPorEmail(email);
            if (usuarioExistente != null) {
                usuarioExistente.setNombre(usuario.getNombreUsuario());
                usuarioExistente.setPassword(usuario.getPassword());
                servicioUsuario.actualizar(usuarioExistente);

                model.put("nombre", usuarioExistente.getNombreUsuario());
                model.put("email", usuarioExistente.getEmail());
                model.put("mensaje", "Perfil actualizado correctamente.");
            }
        } catch (Exception e) {
            model.put("error", "Error al actualizar el perfil: " + e.getMessage());
        }

        // Enviamos los datos al modelo
        model.put("usuario", usuario);
        return new ModelAndView("perfil", model);
    }
}
