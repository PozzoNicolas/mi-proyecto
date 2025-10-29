package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioRecomendaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class ControladorInicio {

    private final ServicioCliente servicioCliente;
    private final ServicioRecomendaciones servicioRecomendaciones;

    @Autowired
    public ControladorInicio(ServicioCliente servicioCliente, ServicioRecomendaciones servicioRecomendaciones) {
        this.servicioCliente = servicioCliente;
        this.servicioRecomendaciones = servicioRecomendaciones;
    }

    @GetMapping("/inicio")
    public String inicio(Model modelo) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(101L);
        modelo.addAttribute("turnos", new ArrayList<>(clienteActual.getTurnos()));
        modelo.addAttribute("recomendaciones", servicioRecomendaciones.generarRecomendaciones(clienteActual));
        return "inicio"; // Thymeleaf busca /WEB-INF/views/thymeleaf/inicio.html
    }
}
