package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List; 

import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.ServicioTurnosImpl;


@Controller
public class ControladorDiaTurno {

    @Autowired
    private ServicioTurnosImpl servicioTurnos; 

    @PostMapping("/seleccionar-dia")
    public ModelAndView seleccionarDia(@ModelAttribute Turno turno) {
        ModelMap modelo = new ModelMap();

        if (turno.getFecha() == null || turno.getFecha().isEmpty()) {
            modelo.put("error", "Debe seleccionar un día");
            modelo.put("turno", turno);
            return new ModelAndView("seleccionar-dia", modelo);
        }

        List<String> horariosDisponibles = servicioTurnos.getHorariosDisponibles(turno.getFecha());
        modelo.put("horarioTurnos", horariosDisponibles);
        modelo.put("turno", turno);

        return new ModelAndView("resultado-turno", modelo);
    }

}
