package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class ControladorPerfil {

    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping
    public ModelAndView verPerfil(HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuarioActual);
        return new ModelAndView("perfil", modelo);
    }

    @GetMapping("/editar")
    public ModelAndView verEditarPerfil(HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuarioActual);
        return new ModelAndView("editar-perfil", modelo);
    }

    @PostMapping("/editar")
    public ModelAndView editarPerfil(
            @ModelAttribute("usuario") Usuario usuarioEditado,
            HttpSession session) {

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuarioEditado);

        if (usuarioEditado.getNombre() == null || usuarioEditado.getNombre().trim().isEmpty()) {
            modelo.put("error", "El nombre no puede quedar vacío.");
            return new ModelAndView("editar-perfil", modelo);
        }

        if (usuarioEditado.getApellido() == null || usuarioEditado.getApellido().trim().isEmpty()) {
            modelo.put("error", "El apellido no puede quedar vacío.");
            return new ModelAndView("editar-perfil", modelo);
        }

        if (usuarioEditado.getTelefono() == null || usuarioEditado.getTelefono().trim().isEmpty()) {
            modelo.put("error", "El teléfono no puede quedar vacío.");
            return new ModelAndView("editar-perfil", modelo);
        }

        if (usuarioEditado.getEmail() == null || usuarioEditado.getEmail().trim().isEmpty()) {
            modelo.put("error", "El email no puede quedar vacío.");
            return new ModelAndView("editar-perfil", modelo);
        }

        usuarioActual.setNombre(usuarioEditado.getNombre());
        usuarioActual.setApellido(usuarioEditado.getApellido());
        usuarioActual.setTelefono(usuarioEditado.getTelefono());
        usuarioActual.setEmail(usuarioEditado.getEmail());

        servicioUsuario.actualizar(usuarioActual);
        session.setAttribute("usuarioActual", usuarioActual);

        return new ModelAndView("redirect:/perfil");
    }


}
