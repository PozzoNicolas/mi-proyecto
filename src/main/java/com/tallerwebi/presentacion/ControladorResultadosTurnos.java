package com.tallerwebi.presentacion;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioTurnosImpl;
import com.tallerwebi.dominio.ServicioVeterinariaImpl;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Veterinaria;

@Controller
public class ControladorResultadosTurnos {
    
    @Autowired
    private ServicioTurnosImpl servicioTurnos;
    @Autowired
    private ServicioVeterinariaImpl servicioVeterinaria; 

    @GetMapping("/mostrar-veterinarias")
    public ModelAndView mostrarVeterinarias() {
        ModelMap modelo = new ModelMap();
        return new ModelAndView("mostrar-veterinarias", modelo);
    }

    /*
    @PostMapping("/seleccionar-turno")
    public ModelAndView seleccionarTurno(@RequestParam("seleccion") String seleccion,
                                        @ModelAttribute("turno") Turno turno) {
        ModelMap modelo = new ModelMap(); 

            Veterinaria v = servicioVeterinaria.buscarPorId(turno.getVeterinaria());
    if (v == null) {
        v = new Veterinaria(); // evita null pointer
    }

        String[] partes = seleccion.split("\\\\|\\\\|");
        if(partes.length != 2) {
            modelo.put("error", "Seleccion invalida");
            return new ModelAndView("resultado-turno", modelo);
        }
        String horario = partes[0];
        String nombre = partes[1];
        turno.setHorario(horario);
        turno.setProfesional(nombre);
        modelo.put("turno", turno);
        modelo.put("veterinaria", v);
        return new ModelAndView("turnos", modelo); 
    } */

}
