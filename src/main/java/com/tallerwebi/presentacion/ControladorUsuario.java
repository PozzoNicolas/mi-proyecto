package com.tallerwebi.presentacion;

import java.util.List;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Controller
@RequestMapping("/clientes")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    // ✅ Inyección automática del servicio desde Spring
    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    // DTO interno para manejar el formulario
    public static class UsuarioDto {
        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y/o espacios")
        private String nombre;

        @NotBlank(message = "El apellido es obligatorio")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido solo puede contener letras y/o espacios")
        private String apellido;

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        private String correo;

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9]{8}$", message = "El teléfono debe tener 8 dígitos")
        private String telefono;

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    // === Métodos del controlador ===
    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = servicioUsuario.listarTodos();
        model.addAttribute("clientes", usuarios);
        model.addAttribute("nuevoCliente", new UsuarioDto());
        return "clientes";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("nuevoCliente") UsuarioDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", servicioUsuario.listarTodos());
            return "clientes";
        }

        Usuario nuevo = new Usuario(dto.getNombre(), dto.getApellido(), dto.getCorreo(), dto.getTelefono());
        servicioUsuario.registrarUsuario(nuevo);
        return "redirect:/clientes";
    }
}

