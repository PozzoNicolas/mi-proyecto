package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, ModelMap modelo) {
        // Obtener el email del usuario desde la sesión
        String email = (String) session.getAttribute("EMAIL");

        if (email == null) {
            // Si no hay usuario logueado, redirigir al login
            return "redirect:/login";
        }

        // Buscar usuario en la base de datos
        Usuario usuario = servicioUsuario.obtenerPorEmail(email);

        if (usuario == null) {
            modelo.put("error", "No se pudo cargar la información del usuario.");
            return "error"; //
        }

        // Pasar los datos del usuario a la vista
        modelo.put("nombreUsuario", usuario.getNombreUsuario());
        modelo.put("apellido", usuario.getApellidoUsuario());
        modelo.put("correo", usuario.getEmail());
        modelo.put("telefono", usuario.getTelefonoUsuario());

        return "perfil"; // nombre del archivo perfil.html
    }
}
