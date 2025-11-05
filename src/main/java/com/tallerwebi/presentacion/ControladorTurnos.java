package com.tallerwebi.presentacion;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.TurnoVistaDTO;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.ServicioTurnos;

@Controller
public class ControladorTurnos {

    private final ServicioUsuario servicioUsuario;
    private final ServicioMail servicioMail;
    private final ServicioTurnos servicioTurnos; 

    @Autowired
    public ControladorTurnos(ServicioUsuario servicioUsuario, ServicioMail servicioMail, ServicioTurnos servicioTurnos) {
        this.servicioUsuario = servicioUsuario;
        this.servicioMail = servicioMail; 
        this.servicioTurnos = servicioTurnos;
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
        Usuario usuarioConTurnos = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioActual.getId());
        
        List<TurnoVistaDTO> turnosDTO = usuarioConTurnos.getTurnos()
            .stream()
            .map((Turno turno) -> servicioTurnos.mapearTurnoATurnoVistaDTO(turno))
            .collect(Collectors.toList());

        modelo.addAttribute("turnos", turnosDTO);

        return "turnos";
    }

    @PostMapping("/cancelar-turno")
    public String cancelarTurno(
            @RequestParam("turnoId") Long turnoId,
            HttpSession session,
            HttpServletRequest request
    ) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) return "redirect:/login";

        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");

        servicioMail.enviarCancelacionDeTurno(usuarioActual, turnoId, emailPorLogin);
        servicioUsuario.cancelarTurno(usuarioActual, turnoId);

        return "redirect:/turnos";
    }
}
