package com.tallerwebi.presentacion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioVPH;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.TurnoDTO;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Veterinaria;
import com.tallerwebi.dominio.VeterinariaProfesionalHorario;
import com.tallerwebi.dominio.enums.Especialidad;

@Controller
@RequestMapping("/turno")
@SessionAttributes("turnoDTO")
public class ControladorSacarTurno {
    
    private final ServicioUsuario servicioUsuario;
    private final ServicioVeterinaria servicioVeterinaria; 
    private final ServicioTurnos servicioTurnos;
    private final ServicioVPH servicioVPH;
    private final ServicioMail servicioMail; 

    @Autowired
    public ControladorSacarTurno(ServicioUsuario servicioUsuario, ServicioVeterinaria servicioVeterinaria, ServicioTurnos servicioTurnos, ServicioVPH servicioVPH, ServicioMail servicioMail) {
        this.servicioUsuario = servicioUsuario;
        this.servicioVeterinaria = servicioVeterinaria; 
        this.servicioTurnos = servicioTurnos; 
        this.servicioVPH = servicioVPH;
        this.servicioMail = servicioMail; 
    }

    //Este model atribute se comparte entre las dos vistas, no? 
    @ModelAttribute
    public void setAtributosComunes(ModelMap modelo, HttpSession session) {
        Usuario usuarioActual =  (Usuario)session.getAttribute("usuarioActual");
        modelo.addAttribute("usuario", usuarioActual); //Para guardar turno en usuario
        modelo.addAttribute("veterinarias", servicioVeterinaria.listarVeterinarias()); //Necesario para primera vista, saber los turnos
        modelo.addAttribute("especialidades",Especialidad.values()); //Para el primer campo, el segundo se llena con js
    }

    @ModelAttribute("turnoDTO")
    public TurnoDTO crearTurnoDTO() {
        return new TurnoDTO();
    }

    @GetMapping("/nuevo-turno")
    public String mostrarNuevosTurnos(Model modelo, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }

        // ensure the model has the turnoDTO (the @ModelAttribute method will supply it)
        // modelo.addAttribute("turnoDTO", new TurnoDTO()); // NOT needed because @ModelAttribute supplies it
        return "nuevo-turno";
    }

    @GetMapping("/resultado-turno")
    public ModelAndView mostrarVeterinarias(@ModelAttribute("turnoDTO") TurnoDTO turnoDTO,
                                            HttpSession session) {
        ModelMap modelo = new ModelMap();
        if (session.getAttribute("usuarioActual") == null){
            return new ModelAndView("home", modelo);
        }

        //Veterinaria v = turno.getVeterinaria();
        Veterinaria v = servicioTurnos.getVeterinariaPorTurnoDTO(turnoDTO);
        //Se carga la/s veterinaria/s que el usuario busco, y se su be al modelo
        //Aca se transforma los datos del VPH en un mapa Transient para comunicarse con el front
        if(turnoDTO.getVeterinariaId() == null || turnoDTO.getVeterinariaId() == 0) { //Vet es nula, el usuario no eligio ninguna...
            List<Veterinaria> todas = servicioVeterinaria.listarVeterinarias();
            for (Veterinaria vet : todas) {
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
        } else { //Vet es un objeto, se carga solo ese
            List<VeterinariaProfesionalHorario> vphList = servicioVPH.obtenerProfesionalesDeVeterinaria(v.getId());
            Map<String, List<Profesional>> mapaHorarioProfesionales = vphList.stream()
                    .collect(Collectors.groupingBy(
                            vph -> vph.getHorario().toString(),
                            Collectors.mapping(VeterinariaProfesionalHorario::getProfesional, Collectors.toList())
                    ));
            v.setProfesionalesEnHorario(mapaHorarioProfesionales); // Mapa como existia antes
            modelo.put("veterinaria", v);
        }

        modelo.put("turnoDTO", turnoDTO);

        return new ModelAndView("resultado-turno", modelo);
    }

    @PostMapping("/validar-datos-turno")
    public ModelAndView validarDatos(@ModelAttribute("turnoDTO") TurnoDTO turnoDTO) {
        ModelMap modelo = new ModelMap();

        if(!servicioTurnos.esTurnoDTOEspPracValidas(turnoDTO)) {
            modelo.put("error", "Debe seleccionar especialidad y práctica");
            modelo.put("datosBusqueda", turnoDTO);
            return new ModelAndView("nuevo-turno", modelo);
        }

        modelo.put("turnoDTO", turnoDTO);
        return new ModelAndView("resultado-turno", modelo);
    }

    @PostMapping("/seleccionar-dia")
    public ModelAndView seleccionarDia(@ModelAttribute("turnoDTO") TurnoDTO turnoDTO) {
        if (turnoDTO.getSeleccion() == null) {
            turnoDTO.setSeleccion("");
        }
        return new ModelAndView("redirect:/turno/resultado-turno");
    }

    @PostMapping("/seleccionar-horario-profesional")
    public String seleccionarHorarioProfesional(@ModelAttribute("turnoDTO") TurnoDTO turnoDTO,
                                                ModelMap modelo,
                                                HttpServletRequest request,
                                                HttpSession session,
                                                SessionStatus status) {

        // 1️⃣ Process selection: this should parse turnoDTO.getSeleccion()
        // e.g. formato esperado: "vetId||horarioString||profId"
        servicioTurnos.procesarSeleccion(turnoDTO);

        // 2️⃣ Load managed user and attach turn
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        Usuario usuarioManaged = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioActual.getId());

        // 3️⃣ Convert DTO -> entity Turno (your service should handle mapping)
        Turno turno = servicioTurnos.crearTurnoConDTO(turnoDTO);
        turno.setUsuario(usuarioManaged);

        // 4️⃣ Save turno
        servicioTurnos.guardarTurno(usuarioManaged, turno);

        // 5️⃣ Update session user's turnos if needed
        // reload user with turnos or update the managed object
        Usuario usuarioConTurnos = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioManaged.getId());
        session.setAttribute("usuarioActual", usuarioConTurnos);

        // 6️⃣ Add to model if you want data on redirect target
        // (not necessary if you redirect)
        modelo.addAttribute("usuario", usuarioConTurnos);

        // 7️⃣ Email confirmation
        String emailPorLogin = (String) request.getSession().getAttribute("EMAIL");
        servicioMail.enviarConfirmacionDeTurno(usuarioConTurnos, turno.getId(), emailPorLogin);

        // Clear the DTO from session because flow finished
        status.setComplete();

        // Redirect to turnos list page (or confirmation)
        return "redirect:/turnos";
    }

}
