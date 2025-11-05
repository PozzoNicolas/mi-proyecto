package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioVPH;
import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Veterinaria;
import com.tallerwebi.dominio.VeterinariaProfesionalHorario;
import com.tallerwebi.dominio.enums.Especialidad;

@Controller
public class ControladorNuevoTurno {

    private final ServicioUsuario servicioUsuario;
    private final ServicioVeterinaria servicioVeterinaria; 
    private final ServicioTurnos servicioTurnos;
    private final ServicioVPH servicioVPH;

    @Autowired
    public ControladorNuevoTurno(ServicioUsuario servicioUsuario, ServicioVeterinaria servicioVeterinaria, ServicioTurnos servicioTurnos, ServicioVPH servicioVPH) {
        this.servicioUsuario = servicioUsuario;
        this.servicioVeterinaria = servicioVeterinaria; 
        this.servicioTurnos = servicioTurnos; 
        this.servicioVPH = servicioVPH;
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

        modelo.addAttribute("veterinarias", servicioVeterinaria.listarVeterinarias());
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

        //Veterinaria v = turno.getVeterinaria();
        Veterinaria v = servicioTurnos.obtenerVeterinariaPorTurno(turno);
        List<VeterinariaProfesionalHorario> vphList = servicioVPH.obtenerProfesionalesDeVeterinaria(v.getId());

        // Esto toma la base de datos, obtiene los veterinarios y sus horarios
        // Los suma a un parametro Transient en la veterinaria
        // Esto es para utilizar la logica que teniamos antes con el front... 
        Map<String, List<Profesional>> mapaHorarioProfesionales = vphList.stream()
                .collect(Collectors.groupingBy(
                        vph -> vph.getHorario().toString(),
                        Collectors.mapping(VeterinariaProfesionalHorario::getProfesional, Collectors.toList())
                ));

        v.setProfesionalesEnHorario(mapaHorarioProfesionales); // you must add this field in Veterinaria (transient)

        modelo.put("veterinaria", v);
        modelo.put("turno", turno);

        modelo.put("veterinaria", v);
        modelo.put("turno", turno);

        return new ModelAndView("resultado-turno", modelo);
    }
}
