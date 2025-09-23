package com.tallerwebi.presentacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioTurnos;

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
        modelo.put("turnos", servicioTurnos.getTurnos());
        return new ModelAndView("turnos", modelo);
    }

}
