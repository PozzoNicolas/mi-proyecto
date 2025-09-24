package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Turno;

@Controller
public class ControladorNuevoTurno {

    @RequestMapping("/nuevo-turno")
    public ModelAndView datosBusqueda() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosBusqueda", new Turno()); //No estoy seguro si deberian de ser objetos diferentes...
        return new ModelAndView("nuevo-turno", modelo); 
    }

    @PostMapping("/validar-datos-turno")
    public ModelAndView validarDatos(@ModelAttribute("datosBusqueda") Turno turno) {
        ModelMap modelo = new ModelMap();
        if (turno.getEspecialidad() == null || turno.getEspecialidad().isEmpty() ||
        turno.getPractica() == null || turno.getPractica().isEmpty() ||
        turno.getLugar() == null || turno.getLugar().isEmpty()) {

        modelo.put("error", "Debe completar todas las opciones");
        modelo.put("datosBusqueda", turno);
        return new ModelAndView("nuevo-turno", modelo);
        }

        modelo.put("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }

}
