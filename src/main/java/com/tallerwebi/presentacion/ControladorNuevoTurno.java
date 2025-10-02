package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.enums.Especialidad;

@Controller
public class ControladorNuevoTurno {

    private final ServicioCliente servicioCliente; 
    private final ServicioVeterinaria servicioVeterinaria; 

    @Autowired
    public ControladorNuevoTurno(ServicioCliente servicioCliente, ServicioVeterinaria servicioVeterinaria) {
        this.servicioCliente = servicioCliente; 
        this.servicioVeterinaria = servicioVeterinaria; 
    }

    @GetMapping("/nuevo-turno")
    public String mostrarNuevosTurnos(Model modelo) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(101); 
        modelo.addAttribute("datosBusqueda", new Turno());
        modelo.addAttribute("veterinarias", servicioVeterinaria.listarVeterinarias());
        modelo.addAttribute("cliente", clienteActual); 
        modelo.addAttribute("especialidades",Especialidad.values());
        return "nuevo-turno";
    }

    @PostMapping("/validar-datos-turno")
    public ModelAndView validarDatos(@ModelAttribute("datosBusqueda") Turno turno) {
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("veterinarias", servicioVeterinaria.listarVeterinarias());
        modelo.addAttribute("cliente", servicioCliente.buscarClientePorId(101));
        modelo.addAttribute("especialidades",Especialidad.values());
        //Integer vetId = turno.getVeterinaria();
        //Veterinaria v = (vetId != null && vetId != 0) ? servicioVeterinaria.buscarPorId(vetId) : null;
        if (turno.getEspecialidad() == null || 
            turno.getPractica() == null) {
            modelo.put("error", "Debe seleccionar especialidad y práctica");
            modelo.put("datosBusqueda", turno);
            return new ModelAndView("nuevo-turno", modelo);
        }


        modelo.put("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }

}
