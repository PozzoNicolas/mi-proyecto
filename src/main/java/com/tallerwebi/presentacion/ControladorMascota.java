package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

        Mascota mascota = new Mascota(dto.getNombre(), dto.getTipoDeMascota(), dto.getRaza(), dto.getEdad(), dto.getSexo());

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
        //Lo hacemos obligatorio ?
        private String raza;

        @Min(value = 0, message = "La edad no puede ser negativa")
        @Max(value = 40, message = "La edad máxima permitida es 40 años")
        private Integer edad;
        private Usuario duenio;

        @NotBlank(message = "El sexo es obligatorio")
        private String sexo;


        // getters y setters


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTipoDeMascota() {
            return tipoDeMascota;
        }

        public void setTipoDeMascota(String tipoDeMascota) {
            this.tipoDeMascota = tipoDeMascota;
        }

        public String getRaza() {
            return raza;
        }

        public void setRaza(String raza) {
            this.raza = raza;
        }

        public Integer getEdad() {
            return edad;
        }

        public void setEdad(Integer edad) {
            this.edad = edad;
        }

        public Usuario getDuenio() {
            return duenio;
        }

        public void setDuenio(Usuario duenio) {
            this.duenio = duenio;
        }

        public String getSexo() {
            return sexo;
        }


        public void setSexo(String sexo) { this.sexo = sexo; }
    }
}
