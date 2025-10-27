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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/mascotas")
public class ControladorMascota {

    private final ServicioMascota servicioMascota;
    private final ServicioCliente servicioCliente;

    @Autowired
    public ControladorMascota(ServicioMascota servicioMascota, ServicioCliente servicioCliente) {
        this.servicioMascota = servicioMascota;
        this.servicioCliente = servicioCliente;
    }

    @GetMapping
    public String listar(Model model) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(100L); // Simulamos login: Juan
        model.addAttribute("mascotas", clienteActual.getMascotas());
        model.addAttribute("nuevaMascota", new MascotaDto());
        return "mascotas";
    }

    @PostMapping
    //El @Valid le pide a Spring que valide las notaciones que pusimo en el dto (notblank, min, max).
    //El Bindingresult guarda los errores de la validación.
    public String crear(@Valid @ModelAttribute("nuevaMascota") MascotaDto dto, BindingResult resultado, Model model) {
        //el .hasError devuelve true si alguna validación no cumplió con las reglas.
        if(resultado.hasErrors()) {
            Cliente clienteActual = servicioCliente.buscarClientePorId(100L);
            model.addAttribute("mascotas", clienteActual.getMascotas());
            return "mascotas";
        }

        Cliente clienteActual = servicioCliente.buscarClientePorId(100L); // Simulamos login
        Mascota mascota = new Mascota(dto.getNombre(), dto.getTipoDeMascota(), dto.getRaza(), dto.getEdad());
        servicioMascota.registrarMascota(clienteActual.getId(), mascota);
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
        private Cliente duenio;


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

        public Cliente getDuenio() {
            return duenio;
        }

        public void setDuenio(Cliente duenio) {
            this.duenio = duenio;
        }
    }
}
