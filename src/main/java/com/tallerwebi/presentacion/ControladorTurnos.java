package com.tallerwebi.presentacion;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioMail;

@Controller
public class ControladorTurnos {

    private final ServicioUsuario servicioUsuario;
    private final ServicioMail servicioMail;

    @Autowired
    public ControladorTurnos(ServicioUsuario servicioUsuario, ServicioMail servicioMail) {
        this.servicioUsuario = servicioUsuario;
        this.servicioMail = servicioMail; 
    }

    @GetMapping("/turnos")
    public String mostrarTurnos(Model modelo, HttpServletRequest request, HttpSession session) {
        String email = (String) request.getSession().getAttribute("EMAIL");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if(usuarioActual == null){
            return "redirect:/login";
        }
        modelo.addAttribute("email", email);
        modelo.addAttribute("turnos", new ArrayList<>(usuarioActual.getTurnos()));
        return "turnos";
    }

    @PostMapping("/cancelar-turno")
    public String cancelarTurno(@RequestParam("turnoId") ModelMap modelo, Integer turnoId,
                                HttpServletRequest request, HttpSession session) {

        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");

        Usuario usuarioActual =  (Usuario)session.getAttribute("usuarioActual");
        modelo.addAttribute("usuario", usuarioActual);
        // Cliente =/= Usuario
        servicioMail.enviarCancelacionDeTurno(usuarioActual, turnoId, emailPorLogin);
        servicioUsuario.cancelarTurno(usuarioActual, turnoId);
        return "redirect:/turnos";
    }

}
