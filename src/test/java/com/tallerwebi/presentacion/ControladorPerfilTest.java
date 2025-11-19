package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;

public class ControladorPerfilTest {

    private ControladorPerfil controladorPerfil;
    private ServicioUsuario servicioUsuarioMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioUsuarioMock = mock(ServicioUsuario.class);
        sessionMock = mock(HttpSession.class);
        controladorPerfil = new ControladorPerfil(servicioUsuarioMock);
    }

    @Test
    public void editarPerfil_conNombreVacio_deberiaVolverAEditarConError() {
        Usuario usuarioSesion = new Usuario();
        when(sessionMock.getAttribute("usuarioActual")).thenReturn(usuarioSesion);

        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNombre("");  // vacío
        usuarioEditado.setApellido("Perez");
        usuarioEditado.setTelefono("123");
        usuarioEditado.setEmail("mail@mail.com");

        ModelAndView mav = controladorPerfil.editarPerfil(usuarioEditado, sessionMock);

        assertEquals("editar-perfil", mav.getViewName());
        assertEquals("El nombre no puede quedar vacío.", mav.getModel().get("error"));

        verify(servicioUsuarioMock, times(0)).actualizar(any());
    }

    @Test
    public void editarPerfil_conDatosValidos_deberiaActualizarYRedirigir() {
        Usuario usuarioSesion = new Usuario();
        usuarioSesion.setNombre("Juan");
        usuarioSesion.setApellido("Lopez");
        usuarioSesion.setTelefono("111");
        usuarioSesion.setEmail("old@mail.com");

        when(sessionMock.getAttribute("usuarioActual")).thenReturn(usuarioSesion);

        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNombre("NuevoNombre");
        usuarioEditado.setApellido("NuevoApellido");
        usuarioEditado.setTelefono("222");
        usuarioEditado.setEmail("nuevo@mail.com");

        ModelAndView mav = controladorPerfil.editarPerfil(usuarioEditado, sessionMock);

        assertEquals("redirect:/perfil", mav.getViewName());
        verify(servicioUsuarioMock, times(1)).actualizar(usuarioSesion);

        assertEquals("NuevoNombre", usuarioSesion.getNombre());
        assertEquals("NuevoApellido", usuarioSesion.getApellido());
        assertEquals("222", usuarioSesion.getTelefono());
        assertEquals("nuevo@mail.com", usuarioSesion.getEmail());
    }

    @Test
    public void editarPerfil_sinUsuarioEnSesion_deberiaRedirigirALogin() {

        when(sessionMock.getAttribute("usuarioActual")).thenReturn(null);

        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNombre("Pepe");

        ModelAndView mav = controladorPerfil.editarPerfil(usuarioEditado, sessionMock);

        assertEquals("redirect:/login", mav.getViewName());
        verify(servicioUsuarioMock, never()).actualizar(any());
    }
}
