package com.tallerwebi.presentacion;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import com.tallerwebi.config.SpringWebConfig;
import com.tallerwebi.dominio.ServicioRecomendaciones;
import com.tallerwebi.dominio.ServicioTurnos;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;

import org.mockito.MockitoAnnotations;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
// Usa tu nueva clase de configuración aquí:
@ContextConfiguration(classes = { SpringWebConfig.class })
public class ControladorInicioTest {

    private ControladorInicio controlador;
    @Mock
    private ServicioUsuario servicioUsuario;
    @Mock
    private ServicioRecomendaciones servicioRecomendaciones;
    @Mock
    private ServicioTurnos servicioTurnos;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Inicializa los Mocks
        MockitoAnnotations.openMocks(this);
        controlador = new ControladorInicio(servicioUsuario, servicioRecomendaciones, servicioTurnos);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html"); // si tus vistas son html

        mockMvc = MockMvcBuilders.standaloneSetup(controlador)
                .setViewResolvers(viewResolver)
                .build();

    }

    @Test
    public void siNoHayUsuarioEnSesion_redirigeAlLogin() throws Exception {
        mockMvc.perform(get("/inicio"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void siHayUsuarioEnSesion_muestraInicio() throws Exception {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(5L);
        usuarioMock.setNombre("Juan");

        when(servicioUsuario.buscarUsuarioPorIdConTurnos(5L))
                .thenReturn(usuarioMock);

        mockMvc.perform(get("/inicio")
                .sessionAttr("usuarioActual", usuarioMock))
                .andExpect(status().isOk())
                .andExpect(view().name("inicio"))
                .andExpect(model().attributeExists("usuarioActual"))
                .andExpect(model().attribute("nombre", "Juan"));
    }

    @Test
    public void siHayUsuarioEnSesion_llamaAlServicioParaCargarTurnos() throws Exception {

        // 1) Usuario simulado en sesión
        Usuario usuarioEnSesion = new Usuario();
        usuarioEnSesion.setId(10L);
        usuarioEnSesion.setNombre("Maria");

        // 2) Usuario que el servicio debería devolver
        Usuario usuarioConTurnos = new Usuario();
        usuarioConTurnos.setId(10L);
        usuarioConTurnos.setNombre("Maria");
        usuarioConTurnos.setTurnos(Collections.emptyList());

        // 3) Defino qué debe devolver el mock del servicio
        when(servicioUsuario.buscarUsuarioPorIdConTurnos(10L))
                .thenReturn(usuarioConTurnos);

        // 4) Ejecutamos el controlador simulando que hay usuario logueado
        mockMvc.perform(get("/inicio")
                .sessionAttr("usuarioActual", usuarioEnSesion))
                .andExpect(status().isOk());

        // 5) Verifico que el servicio se llamó correctamente
        verify(servicioUsuario, times(1)).buscarUsuarioPorIdConTurnos(10L);
    }
}
