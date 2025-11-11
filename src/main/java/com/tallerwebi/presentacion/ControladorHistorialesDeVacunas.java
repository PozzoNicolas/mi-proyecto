package com.tallerwebi.presentacion;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.HistorialDeVacunas;
import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioHistorialDeVacunas;
import com.tallerwebi.dominio.ServicioMascota;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
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

    @GetMapping("/modificar-historial-de-vacunacion")
    public ModelAndView modificarHistorialDeVacunas(
            @RequestParam(value = "id", required = false) Long id,
            HttpSession session) {

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        if (id == null) {
            // fallback: go back to the list
            return new ModelAndView("redirect:/historiales-de-vacunacion");
        }

        HistorialDeVacunas historial = servicioHistorialDeVacunas.getPorId(id);
        ModelMap modelo = new ModelMap();
        modelo.put("usuarioActual", usuarioActual);
        modelo.put("historial", historial);
        modelo.put("mascota", historial.getMascota());
        modelo.put("vacunas", Vacuna.values());

        modelo.addAttribute("nuevaVacunacion", new VacunacionDTO());

        return new ModelAndView("modificar-historial-de-vacunacion", modelo);
    }
}
