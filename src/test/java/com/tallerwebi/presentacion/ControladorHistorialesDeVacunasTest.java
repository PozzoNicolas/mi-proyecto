package com.tallerwebi.presentacion;

import com.tallerwebi.config.SpringWebConfig;
import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class})
public class ControladorHistorialesDeVacunasTest {

    @Mock
    private ServicioUsuario servicioUsuario;
    @Mock
    private ServicioHistorialDeVacunas servicioHistorialDeVacunas;
    @Mock
    private ServicioMascota servicioMascota;
    @Mock
    private HttpSession session;

    private ControladorHistorialesDeVacunas controlador;
    private MockMvc mockMvc;

    private Usuario usuario;
    private HistorialDeVacunas historial;

    /*

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        controlador = new ControladorHistorialesDeVacunas(servicioUsuario, servicioHistorialDeVacunas, servicioMascota);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controlador)
                .setViewResolvers(viewResolver)
                .build();

        usuario = new Usuario();
        usuario.setId(1L);

        Mascota mascota = new Mascota();
        mascota.setFechaDeNacimiento(LocalDate.of(2020, 1, 1)); // ✅ add this line

        historial = new HistorialDeVacunas();
        historial.setId(10L);
        historial.setMascota(mascota);
    }


    @Test
    public void guardarVacuna_deberiaGuardarYMostrarVistaDeHistorial() throws Exception {
        when(session.getAttribute("usuarioActual")).thenReturn(usuario);
        when(servicioHistorialDeVacunas.getPorId(10L)).thenReturn(historial);

        mockMvc.perform(post("/guardar-vacuna")
                        .sessionAttr("usuarioActual", usuario)
                        .param("historialId", "10")
                        .param("vacuna", "HEPATITIS")
                        .param("fecha", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("modificar-historial-de-vacunacion"))
                .andExpect(model().attributeExists("mensajeExito"));

        verify(servicioHistorialDeVacunas, times(1)).guardar(historial);
    } */
}
