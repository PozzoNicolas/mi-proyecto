package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioTurnosImpl;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.ServicioVeterinariaImpl;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Veterinaria;

@Controller
public class ControladorResultadosTurnos {

    public final ServicioVeterinaria servicioVeterinaria;
    public final ServicioCliente servicioCliente; 

    @Autowired
    public ControladorResultadosTurnos(ServicioVeterinaria servicioVeterinaria, ServicioCliente servicioCliente) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.servicioCliente = servicioCliente; 
    }

    @GetMapping("/resultado-turno")
    public ModelAndView mostrarVeterinarias() {
        ModelMap modelo = new ModelMap();
        Turno turno = new Turno(); 
        modelo.addAttribute("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }


    @PostMapping("/seleccionar-dia")
    public ModelAndView seleccionarDia(@ModelAttribute("turno") Turno turno) {
        ModelMap modelo = new ModelMap();

        

        if (servicioVeterinaria.buscarPorId(turno.getVeterinaria()) == null) {
                // Caso "Indiferente": traer todas las veterinarias
                List<Veterinaria> todasVeterinarias = servicioVeterinaria.listarVeterinarias();
                modelo.addAttribute("veterinarias", todasVeterinarias);
            } else {
                // Veterinaria específica
                Veterinaria v = servicioVeterinaria.buscarPorId(turno.getVeterinaria());
                if (v == null) v = new Veterinaria(); // objeto vacío para no romper la vista
                modelo.addAttribute("veterinaria", v);
        }

        modelo.addAttribute("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }

    @PostMapping("/seleccionar-horario-profesional")
    public String seleccionarHorarioProfesional(@ModelAttribute("turno") Turno turno) {
        if (turno.getSeleccion() != null) {
            String[] partes = turno.getSeleccion().split("\\|\\|");
            
            if (partes.length == 3) { // Caso "Indiferente"
                turno.setVeterinaria(Integer.parseInt(partes[0]));
                turno.setHorario(partes[1]);
                turno.setProfesional(partes[2]);
            } else if (partes.length == 2) { // Veterinaria específica
                turno.setHorario(partes[0]);
                turno.setProfesional(partes[1]);
            }
        }

        Cliente clienteActual = servicioCliente.buscarClientePorId(101);
        clienteActual.getTurnos().add(turno);

        ModelMap modelo = new ModelMap();
        modelo.addAttribute("turno", turno);
        modelo.addAttribute("turnos", new ArrayList<>(clienteActual.getTurnos()));
        return "redirect:/turnos";
    }

}
