package com.tallerwebi.presentacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.Turno;

@Controller
public class ControladorTurnos {

    private final ServicioTurnos servicioTurnos; //Eso compartiria turnos entre usuarios, no? 

    @Autowired
    public ControladorTurnos(ServicioTurnos servicioTurnos) {
        this.servicioTurnos = servicioTurnos; 
    }

    @GetMapping("/turnos")
    public ModelAndView mostrarTurnos() {
        ModelMap modelo = new ModelMap();
        List<Turno> turnosProgramados = servicioTurnos.getTurnos(); 
        
        if(turnosProgramados == null  || turnosProgramados.isEmpty())  {
            modelo.put("mensaje","no hay turnos programados"); 
        } else {
            modelo.put("turnos", turnosProgramados);
        }
        return new ModelAndView("turnos", modelo); 
    }
}
