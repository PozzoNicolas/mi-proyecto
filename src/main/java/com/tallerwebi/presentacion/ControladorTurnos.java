package com.tallerwebi.presentacion;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioMail;

@Controller
public class ControladorTurnos {

    private final ServicioCliente servicioCliente; 
    private final ServicioMail servicioMail;

    @Autowired
    public ControladorTurnos(ServicioCliente servicioCliente, ServicioMail servicioMail) {
        this.servicioCliente = servicioCliente; 
        this.servicioMail = servicioMail; 
    }

    @GetMapping("/turnos")
    public String mostrarTurnos(Model modelo, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("EMAIL");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        Cliente clienteActual = servicioCliente.buscarClientePorId(102);
        modelo.addAttribute("email", email);
        modelo.addAttribute("turnos", new ArrayList<>(clienteActual.getTurnos()));
        return "turnos";
    }

    @PostMapping("/cancelar-turno")
    public String cancelarTurno(@RequestParam("turnoId") Integer turnoId,
                                HttpServletRequest request) {

        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");

        Cliente clienteActual = servicioCliente.buscarClientePorId(102);
        // Cliente =/= Usuario
        servicioMail.enviarCancelacionDeTurno(clienteActual, turnoId, emailPorLogin);
        servicioCliente.cancelarTurno(clienteActual, turnoId);  
        return "redirect:/turnos";
    }

}
