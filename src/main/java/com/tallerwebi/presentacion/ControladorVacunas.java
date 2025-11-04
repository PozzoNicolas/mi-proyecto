package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vacunas")
public class ControladorVacunas {

    @GetMapping
    public String mostrarVacunas(Model model) {
        // Podés enviar datos al modelo más adelante, por ejemplo una lista de vacunas
        model.addAttribute("titulo", "Calendario de Vacunación");
        model.addAttribute("descripcion", "Conocé las vacunas recomendadas para tus mascotas según su edad y especie.");
        return "vacunas"; // Thymeleaf buscará /WEB-INF/views/thymeleaf/vacunas.html
    }
}
