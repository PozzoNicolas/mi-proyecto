package com.tallerwebi.presentacion;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;

@Controller
public class ControladorResultadosTurnos {

    public final ServicioVeterinaria servicioVeterinaria;
    public final ServicioCliente servicioCliente; 
    public final ServicioTurnos servicioTurno;
    private final ServicioMail servicioMail;

    @Autowired
    public ControladorResultadosTurnos(ServicioVeterinaria servicioVeterinaria, ServicioCliente servicioCliente, ServicioTurnos servicioTurnos, ServicioMail servicioMail) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.servicioCliente = servicioCliente; 
        this.servicioTurno = servicioTurnos; 
        this.servicioMail = servicioMail;
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

        if (servicioVeterinaria.buscarPorId(turno.getVeterinaria()).getNombre() == null) {
            modelo.addAttribute("veterinarias", servicioTurno.listarVeterinariasIndiferente());
        } else {
            modelo.addAttribute("veterinaria", servicioTurno.obtenerVeterinariaPorTurno(turno));
        }

        modelo.addAttribute("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }

    @PostMapping("/seleccionar-horario-profesional")
    public String seleccionarHorarioProfesional(@ModelAttribute("turno") Turno turno,
                                            HttpServletRequest request) {        
        servicioTurno.procesarSeleccion(turno);

        Cliente clienteActual = servicioCliente.buscarClientePorId(101);
        servicioTurno.guardarTurno(clienteActual, turno); 
        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");

        //Cliente =/= Usuairo. A cambiar 
        servicioMail.enviarConfirmacionDeTurno(clienteActual, turno.getId(), emailPorLogin); 
        return "redirect:/turnos";
    }

}
