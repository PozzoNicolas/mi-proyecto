package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioPDF; // Nuevo servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorCredencial {

    private final ServicioUsuario servicioUsuario;
    private final ServicioPDF servicioPDF; // Inyectamos el servicio PDF

    @Autowired
    public ControladorCredencial(ServicioUsuario servicioUsuario, ServicioPDF servicioPDF) {
        this.servicioUsuario = servicioUsuario;
        this.servicioPDF = servicioPDF;
    }

    @GetMapping("/credencial/descargar")
    public void descargarCredencial(HttpSession session, HttpServletResponse response) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");

        if (usuarioActual == null) {
            // Si no hay usuario en sesión, redirigir al login (manejo básico)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 1. Obtener los datos completos del usuario (nombre, apellido y mascotas)
        // Usamos el servicio de usuario para obtener las mascotas (asumiendo que las carga)
        Usuario usuarioConMascotas = servicioUsuario.buscarUsuarioPorId(usuarioActual.getId());

        // 2. Configurar la respuesta HTTP para la descarga
        response.setContentType("application/pdf");
        String nombreArchivo = "Credencial_" + usuarioActual.getNombre() + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

        try {
            // 3. Generar el PDF y escribirlo directamente en el stream de respuesta
            servicioPDF.generarCredencial(usuarioConMascotas, response.getOutputStream());
            response.getOutputStream().flush();

        } catch (Exception e) {
            System.err.println("Error al generar o descargar el PDF: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}