package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioRecomendaciones;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;

@Controller
public class ControladorInicio {

    @GetMapping
    public ModelAndView irAlInicio(HttpSession session){
        Usuario usuarioActual =  (Usuario)session.getAttribute("usuarioActual");
        ModelMap modelo = new ModelMap();

        if (usuarioActual == null){
            return new ModelAndView("redirect:/login");
        }
         modelo.put("usuarioActual", usuarioActual);
        modelo.put("nombre",  usuarioActual.getNombre());

        return new ModelAndView("inicio", modelo);
    }

    private final ServicioUsuario servicioUsuario;
    private final ServicioRecomendaciones servicioRecomendaciones;

    @Autowired
    public ControladorInicio(ServicioUsuario servicioUsuario, ServicioRecomendaciones servicioRecomendaciones) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRecomendaciones = servicioRecomendaciones;
    }

    @GetMapping("/inicio")
    public String inicio(Model modelo, HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return "redirect:/inicio";
        }
        modelo.addAttribute("turnos", new ArrayList<>(usuarioActual.getTurnos()));
        modelo.addAttribute("recomendaciones", servicioRecomendaciones.generarRecomendaciones(usuarioActual));
        return "inicio"; // Thymeleaf busca /WEB-INF/views/thymeleaf/inicio.html
    }
}
