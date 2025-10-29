package com.tallerwebi.presentacion;
import java.util.List;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioUsuarioImpl;

@Controller
@RequestMapping("/clientes")
public class ControladorUsuario {

    //  Según el profe, los servicios están en dominio. acá inyectamos la impl manualmente
    private final ServicioUsuario servicio = new ServicioUsuarioImpl();

    // DTO para formulario (va dentro del controller)
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
//        guardamos solo los dígitos locales (sin prefijo +54 9 11)
        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9]{8}$", message = "El teléfono debe tener 8 dígitos")
        private String telefono;

        // getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = servicio.listarTodos();
        model.addAttribute("clientes", usuarios);
        model.addAttribute("nuevoCliente", new UsuarioDto());
        return "clientes";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("nuevoCliente") UsuarioDto dto, BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            // volver a cargar datos necesarios para la vista
            model.addAttribute("clientes", servicio.listarTodos());
            return "clientes"; // misma vista del formulario
        }
        Usuario c = new Usuario(dto.getNombre(), dto.getApellido(), dto.getCorreo(), dto.getTelefono());
        servicio.registrarUsuario(c);
        return "redirect:/clientes";
    }


}
