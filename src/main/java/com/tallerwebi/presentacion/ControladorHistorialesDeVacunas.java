package com.tallerwebi.presentacion;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.HistorialDeVacunas;
import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioHistorialDeVacunas;
import com.tallerwebi.dominio.ServicioMascota;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vacunacion;
import com.tallerwebi.dominio.VacunacionDTO;
import com.tallerwebi.dominio.enums.Vacuna;

@Controller
public class ControladorHistorialesDeVacunas {
    //Controlador que maneja dos vistas:
    //"historiales-de-vacunacion" y "modificar-historial-de-vacunacion"
    
    private final ServicioUsuario servicioUsuario;
    private final ServicioHistorialDeVacunas servicioHistorialDeVacunas;
    private final ServicioMascota servicioMascota;

    @Autowired
    public ControladorHistorialesDeVacunas(ServicioUsuario servicioUsuario, ServicioHistorialDeVacunas servicioHistorialDeVacunas, ServicioMascota servicioMascota) {
        this.servicioUsuario = servicioUsuario;
        this.servicioHistorialDeVacunas = servicioHistorialDeVacunas;
        this.servicioMascota = servicioMascota;
    }

    @GetMapping("/historiales-de-vacunacion") 
    public ModelAndView verMisHistoriales(HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        ModelMap modelo = new ModelMap();

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }
        List<Mascota> mascotas = usuarioActual.getMascotas();
        modelo.put("usuarioActual", usuarioActual);
        modelo.put("mascotas", mascotas); 
        return new ModelAndView("historiales-de-vacunacion", modelo);
    }

    private ModelAndView prepararVistaHistorial(Usuario usuarioActual, HistorialDeVacunas historial, String mensajeExito) {
        Mascota mascota = historial.getMascota();

        List<Vacuna> vacunasFiltradas = Arrays.stream(Vacuna.values())
                .filter(v -> v.getTipoDeMascota().equals(mascota.getTipoDeMascota()))
                .collect(Collectors.toList());

        ModelMap modelo = new ModelMap();
        modelo.addAttribute("usuarioActual", usuarioActual);
        modelo.addAttribute("historial", historial);
        modelo.addAttribute("mascota", mascota);
        modelo.addAttribute("vacunas", vacunasFiltradas);
        modelo.addAttribute("minDate", java.sql.Date.valueOf(historial.getMascota().getFechaDeNacimiento()));
        modelo.addAttribute("hoy", java.sql.Date.valueOf(LocalDate.now()));
        modelo.addAttribute("nuevaVacunacion", new VacunacionDTO());

        if (mensajeExito != null) {
            modelo.addAttribute("mensajeExito", mensajeExito);
        }

        return new ModelAndView("modificar-historial-de-vacunacion", modelo);
    }

    @GetMapping("/modificar-historial-de-vacunacion")
    public ModelAndView modificarHistorialDeVacunas(
            @RequestParam(value = "id", required = false) Long id,
            HttpSession session) {

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        if (id == null) {
            return new ModelAndView("redirect:/historiales-de-vacunacion");
        }

        HistorialDeVacunas historial = servicioHistorialDeVacunas.getPorId(id);
        return prepararVistaHistorial(usuarioActual, historial, null);
    }

    @PostMapping("/guardar-vacuna")
    public ModelAndView guardarVacuna(@ModelAttribute("nuevaVacunacion") VacunacionDTO dto, HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        HistorialDeVacunas historial = servicioHistorialDeVacunas.getPorId(dto.getHistorialId());
        if (historial == null) {
            return new ModelAndView("redirect:/historiales-de-vacunacion");
        }

        Vacunacion nuevaVacunacion = new Vacunacion(
            dto.getVacuna(),
            dto.getFecha()
        );

        historial.agregarVacunacion(nuevaVacunacion);

        return prepararVistaHistorial(usuarioActual, historial, "Vacuna registrada correctamente.");
    }
}