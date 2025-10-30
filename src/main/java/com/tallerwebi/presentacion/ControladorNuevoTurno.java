package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Veterinaria;
import com.tallerwebi.dominio.enums.Especialidad;

@Controller
public class ControladorNuevoTurno {

    private final ServicioUsuario servicioUsuario;
    private final ServicioVeterinaria servicioVeterinaria; 
    private final ServicioTurnos servicioTurnos;

    @Autowired
    public ControladorNuevoTurno(ServicioUsuario servicioUsuario, ServicioVeterinaria servicioVeterinaria, ServicioTurnos servicioTurnos) {
        this.servicioUsuario = servicioUsuario;
        this.servicioVeterinaria = servicioVeterinaria; 
        this.servicioTurnos = servicioTurnos; 
    }

    @ModelAttribute
    public void setAtributosComunes(ModelMap modelo, HttpSession session) {
        Usuario usuarioActual =  (Usuario)session.getAttribute("usuarioActual");
        modelo.addAttribute("usuario", usuarioActual);
        modelo.addAttribute("veterinarias", servicioVeterinaria.listarVeterinarias());
        modelo.addAttribute("especialidades",Especialidad.values());
    }

    @GetMapping("/nuevo-turno")
    public String mostrarNuevosTurnos(Model modelo, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null){
            return "redirect:/inicio";
        }
        modelo.addAttribute("datosBusqueda", new Turno());
        return "nuevo-turno";
    }

    @PostMapping("/validar-datos-turno")
    public ModelAndView validarDatos(@ModelAttribute("datosBusqueda") Turno turno) {
        ModelMap modelo = new ModelMap();

        if(!servicioTurnos.esTurnoValido(turno)) {
            modelo.put("error", "Debe seleccionar especialidad y práctica");
            modelo.put("datosBusqueda", turno);
            return new ModelAndView("nuevo-turno", modelo);
        }

        Veterinaria v = servicioVeterinaria.buscarPorId(turno.getVeterinaria());

        modelo.put("veterinaria", v);
        modelo.put("turno", turno);

        return new ModelAndView("resultado-turno", modelo);
    }
}
