package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioCliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMisMascotas {

    private final ServicioCliente servicioCliente;

    public ControladorMisMascotas(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(Model model) {
        Cliente cliente = servicioCliente.buscarClientePorId(100L); // por el momento usamos el cliente con ID 100

        List<Mascota> mascotas;
        if (cliente != null) {
            mascotas = cliente.getMascotas();
        } else {
            mascotas = new ArrayList<>();
        }
        model.addAttribute("mascotas", mascotas);
        return "mis-mascotas";
    }
}