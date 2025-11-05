package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.TurnoVistaDTO;
import com.tallerwebi.dominio.ServicioRecomendaciones;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorInicio {

    private final ServicioUsuario servicioUsuario;
    private final ServicioRecomendaciones servicioRecomendaciones;
    private final ServicioTurnos servicioTurnos;

    @Autowired
    public ControladorInicio(ServicioUsuario servicioUsuario, ServicioRecomendaciones servicioRecomendaciones, ServicioTurnos servicioTurnos) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRecomendaciones = servicioRecomendaciones;
        this.servicioTurnos = servicioTurnos;
    }

    @GetMapping("/inicio")
    public ModelAndView irAlInicio(HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        ModelMap modelo = new ModelMap();

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuarioConTurnos = servicioUsuario.buscarUsuarioPorIdConTurnos(usuarioActual.getId());

        modelo.put("usuarioActual", usuarioActual);
        modelo.put("nombre", usuarioActual.getNombre());
        modelo.put("mensajeBienvenida", "¡Bienvenido " + usuarioActual.getNombre() + "!");

        List<TurnoVistaDTO> turnosDTO = usuarioConTurnos.getTurnos()
            .stream()
            .map((Turno turno) -> servicioTurnos.mapearTurnoATurnoVistaDTO(turno))
            .collect(Collectors.toList());

        modelo.addAttribute("turnos", turnosDTO);

        modelo.put("recomendaciones", servicioRecomendaciones.generarRecomendaciones(usuarioActual));

        return new ModelAndView("inicio", modelo);
    }

}
