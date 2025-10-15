package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class ControladorInicio {

    private final ServicioCliente servicioCliente;

    @Autowired
    public ControladorInicio(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @GetMapping("/inicio")
    public String inicio(Model modelo) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(101);
        modelo.addAttribute("turnos", new ArrayList<>(clienteActual.getTurnos()));
        return "inicio"; // Thymeleaf busca /WEB-INF/views/thymeleaf/inicio.html
    }
}
