package com.tallerwebi.presentacion;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;

@Controller
public class ControladorTurnos {

    private final ServicioCliente servicioCliente; 

    @Autowired
    public ControladorTurnos(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente; 
    }

    @GetMapping("/turnos")
    public String mostrarTurnos(Model modelo) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(101);
        modelo.addAttribute("turnos", new ArrayList<>(clienteActual.getTurnos()));
        return "turnos";
    }

}
