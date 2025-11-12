package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/mascotas")
public class ControladorMascota {

    private final ServicioMascota servicioMascota;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorMascota(ServicioMascota servicioMascota, ServicioUsuario servicioUsuario) {
        this.servicioMascota = servicioMascota;
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            return "redirect:/login";
        }
        model.addAttribute("mascotas", usuarioActual.getMascotas());
        model.addAttribute("nuevaMascota", new MascotaDto());
        model.addAttribute("fechaActual", LocalDate.now());
        return "mascotas";
    }

    @PostMapping
    //El @Valid le pide a Spring que valide las notaciones que pusimo en el dto (notblank, min, max).
    //El Bindingresult guarda los errores de la validación.
    public String crear(@Valid @ModelAttribute("nuevaMascota") MascotaDto dto, BindingResult resultado,
                        Model model, HttpSession session) {
        //el .hasError devuelve true si alguna validación no cumplió con las reglas.
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null){
            return "redirect:/login";
        }
        if(resultado.hasErrors()) {
            model.addAttribute("mascotas", usuarioActual.getMascotas());
            return "mascotas";
        }

System.out.println("DEBUG - MascotaDto: nombre=" + dto.getNombre()
            + ", tipo=" + dto.getTipoDeMascota()
            + ", raza=" + dto.getRaza()
            + ", fecha=" + dto.getFechaDeNacimiento()
            + ", sexo=" + dto.getSexo());

        Integer edad = 0;
        if(dto.getFechaDeNacimiento() != null) {
            edad = Period.between(dto.getFechaDeNacimiento(), LocalDate.now()).getYears();
        }

        Mascota mascota = new Mascota(
            dto.getNombre(),
            dto.getTipoDeMascota(),
            dto.getRaza(),
            edad,
            dto.getSexo(),
            dto.getFechaDeNacimiento()
        );

        servicioMascota.registrarMascota(usuarioActual.getId(), mascota);
        Usuario usuarioActualizado = servicioUsuario.buscarUsuarioPorId(usuarioActual.getId());
        session.setAttribute("usuarioActual", usuarioActualizado);
        return "redirect:/mascotas";
    }

    // DTO para formulario
    public static class MascotaDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de mascota es obligatorio")
    private String tipoDeMascota;

    private String raza;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDeNacimiento;

    private Usuario duenio;

    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;

    // --- getters & setters ---
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoDeMascota() { return tipoDeMascota; }
    public void setTipoDeMascota(String tipoDeMascota) { this.tipoDeMascota = tipoDeMascota; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public LocalDate getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

    public Usuario getDuenio() { return duenio; }
    public void setDuenio(Usuario duenio) { this.duenio = duenio; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}
}
