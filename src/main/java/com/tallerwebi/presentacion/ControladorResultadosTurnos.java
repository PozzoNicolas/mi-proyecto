package com.tallerwebi.presentacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorResultadosTurnos {

    public final ServicioVeterinaria servicioVeterinaria;
    public final ServicioUsuario servicioUsuario;
    public final ServicioTurnos servicioTurno;
    private final ServicioMail servicioMail;

    @Autowired
    public ControladorResultadosTurnos(ServicioVeterinaria servicioVeterinaria, ServicioUsuario servicioUsuario, ServicioTurnos servicioTurnos, ServicioMail servicioMail) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.servicioUsuario = servicioUsuario;
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
                                           ModelMap modelo,
                                           HttpServletRequest request,
                                           HttpSession session) {

    // 1️⃣ Process the selection
    servicioTurno.procesarSeleccion(turno);

    // 2️⃣ Fetch managed Usuario from DB with turnos initialized
    Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
    Usuario usuarioManaged = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioActual.getId());

    // 3️⃣ Link Turno back to Usuario
    turno.setUsuario(usuarioManaged);

    // 4️⃣ Save the turno
    servicioTurno.guardarTurno(usuarioManaged, turno);

    // 5️⃣ Update the session with the managed entity
    session.setAttribute("usuarioActual", usuarioManaged);

    // 6️⃣ Add to model
    modelo.addAttribute("usuario", usuarioManaged);

    // 7️⃣ Send confirmation email
    String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");
    servicioMail.enviarConfirmacionDeTurno(usuarioManaged, turno.getId(), emailPorLogin);

    return "redirect:/turnos";
}

}
