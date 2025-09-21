package com.tallerwebi.presentacion;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioClienteImpl;

@Controller
@RequestMapping("/clientes")
public class ControladorCliente {

    //  Según el profe, los servicios están en dominio. acá inyectamos la impl manualmente
    private final ServicioCliente servicio = new ServicioClienteImpl();

    // DTO para formulario (va dentro del controller)
    public static class ClienteDto {
        private String nombre;
        private String apellido;
        private String correo;
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
        List<Cliente> clientes = servicio.listarTodos();
        model.addAttribute("clientes", clientes);
        model.addAttribute("nuevoCliente", new ClienteDto());
        return "clientes";
    }

    @PostMapping
    public String crear(@ModelAttribute("nuevoCliente") ClienteDto dto) {
        Cliente c = new Cliente(null, dto.getNombre(), dto.getApellido(), dto.getCorreo(), dto.getTelefono());
        servicio.registrarCliente(c);
        return "redirect:/clientes";
    }


}
