package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioTurnosImpl;
import com.tallerwebi.dominio.Turno;

@Controller
public class ControladorResultadosTurnos {
    
    @Autowired
    private ServicioTurnosImpl servicioTurnos;

    @GetMapping("/resultado-turno")
    public ModelAndView mostrarFormulario(@ModelAttribute("turno") Turno turno) {
        ModelMap modelo = new ModelMap();
        modelo.put("turno", new Turno()); 
        return new ModelAndView("resultado-turno", modelo);
    }


    @PostMapping("/seleccionar-turno")
    public ModelAndView seleccionarTurno(@ModelAttribute Turno turno) {
        ModelMap modelo = new ModelMap();

        if (turno.getHorario() == null || turno.getHorario().isEmpty()) {
            modelo.put("error", "Debe seleccionar un horario");
            modelo.put("turno", turno);
            return new ModelAndView("seleccionar-horario", modelo);
        }

        // register turno
        servicioTurnos.registrarTurno(turno);

        // redirect to avoid duplicate submission
        return new ModelAndView("redirect:/turnos");
    }


}
