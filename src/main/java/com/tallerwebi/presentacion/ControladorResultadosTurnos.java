package com.tallerwebi.presentacion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

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
    private final ServicioVPH servicioVPH;

    @Autowired
    public ControladorResultadosTurnos(ServicioVeterinaria servicioVeterinaria, ServicioUsuario servicioUsuario, ServicioTurnos servicioTurnos, ServicioMail servicioMail, ServicioVPH servicioVPH) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.servicioUsuario = servicioUsuario;
        this.servicioTurno = servicioTurnos; 
        this.servicioMail = servicioMail;
        this.servicioVPH = servicioVPH; 
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

        Long idVet = turno.getIdVeterinariaBusqueda() == 0 ? null : (long) turno.getIdVeterinariaBusqueda();

        if (idVet == null) {
            // Indiferente: todas las veterinarias
            List<Veterinaria> todas = servicioVeterinaria.listarVeterinarias();
            for (Veterinaria vet : todas) {
                // Fetch horarios + profesionales for each vet
                List<VeterinariaProfesionalHorario> vphList = servicioVPH.obtenerProfesionalesDeVeterinaria(vet.getId());
                Map<String, List<Profesional>> mapa = vphList.stream()
                    .filter(vph -> vph.getHorario() != null && vph.getProfesional() != null)
                    .collect(Collectors.groupingBy(
                        vph -> vph.getHorario().toString(),
                        Collectors.mapping(VeterinariaProfesionalHorario::getProfesional, Collectors.toList())
                    ));
                vet.setProfesionalesEnHorario(mapa);
            }
            modelo.put("veterinarias", todas);

        } else {
            // Veterinaria específica
            Veterinaria vet = servicioVeterinaria.buscarPorId(idVet);
            if (vet != null) {
                List<VeterinariaProfesionalHorario> vphList = servicioVPH.obtenerProfesionalesDeVeterinaria(vet.getId());
                Map<String, List<Profesional>> mapa = vphList.stream()
                    .filter(vph -> vph.getHorario() != null && vph.getProfesional() != null)
                    .collect(Collectors.groupingBy(
                        vph -> vph.getHorario().toString(),
                        Collectors.mapping(VeterinariaProfesionalHorario::getProfesional, Collectors.toList())
                    ));
                vet.setProfesionalesEnHorario(mapa);
            }
            modelo.put("veterinaria", vet);
        }

        modelo.put("turno", turno);
        return new ModelAndView("resultado-turno", modelo);
    }



    @PostMapping("/seleccionar-horario-profesional")
    public String seleccionarHorarioProfesional(@ModelAttribute("turno") Turno turno,
                                            ModelMap modelo,
                                            HttpServletRequest request,
                                            HttpSession session) {

        // 1️⃣ Process selection (sets hora, vet, profesional)
        servicioTurno.procesarSeleccion(turno);

        // 2️⃣ Load user managed entity with turnos
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        Usuario usuarioManaged = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioActual.getId());

        // 3️⃣ Link turno to user
        turno.setUsuario(usuarioManaged);

        // 4️⃣ Save turn
        servicioTurno.guardarTurno(usuarioManaged, turno);

        // 5️⃣ Update session
        session.setAttribute("usuarioActual", usuarioManaged);

        // 6️⃣ Add to model
        modelo.addAttribute("usuario", usuarioManaged);

        // 7️⃣ Email confirmation
        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");
        servicioMail.enviarConfirmacionDeTurno(usuarioManaged, turno.getId(), emailPorLogin);

        return "redirect:/turnos";
    }


}
