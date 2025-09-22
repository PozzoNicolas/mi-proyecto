package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.Turno;

@Controller
public class ControladorResultadosTurnos {
    
    @Autowired
    private ServicioTurnos servicioTurnos;
 /*
@GetMapping("/resultado-turno")
public ModelAndView mostrarFormulario() {
    ModelMap modelo = new ModelMap();
    modelo.put("horarioSeleccionado", new Turno()); 
    return new ModelAndView("resultado-turno", modelo);
}


@PostMapping("/seleccionar-turno")
public ModelAndView registrarTurno(@ModelAttribute("horarioSeleccionado") Turno turno) {
    System.out.println("Horario seleccionado: " + turno.getHorario()); // Debug
    servicioTurnos.registrarTurno(turno);

    ModelMap modelo = new ModelMap();
    modelo.put("turnos", servicioTurnos.getTurnos());
    return new ModelAndView("turnos", modelo); 
} */

}
