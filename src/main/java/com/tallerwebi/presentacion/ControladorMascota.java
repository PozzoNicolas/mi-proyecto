package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Cliente clienteActual = servicioCliente.buscarClientePorId(100); // Simulamos login: Juan
        model.addAttribute("mascotas", clienteActual.getMascotas());
        model.addAttribute("nuevaMascota", new MascotaDto());
        return "mascotas";
    }

    @PostMapping
    public String crear(@ModelAttribute("nuevaMascota") MascotaDto dto) {
        Cliente clienteActual = servicioCliente.buscarClientePorId(100); // Simulamos login
        Mascota mascota = new Mascota(dto.getNombre(), dto.getTipoDeMascota(), dto.getRaza(), dto.getEdad());
        servicioMascota.registrarMascota(clienteActual.getId(), mascota);
        return "redirect:/mascotas";
    }

    // DTO para formulario
    public static class MascotaDto {
        private String nombre;
        private String tipoDeMascota;
        private String raza;
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
