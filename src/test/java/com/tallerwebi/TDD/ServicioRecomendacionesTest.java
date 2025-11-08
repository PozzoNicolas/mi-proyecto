package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ServicioRecomendacionesTest {

    @Mock
    private RepositorioRecomendacion repositorioRecomendacionMock;

    @InjectMocks
    private ServicioRecomendacionesImpl servicioRecomendaciones;

    @BeforeEach
    public void init (){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void queSeGenereUnaRecomendacionParaUnaMascotaConCriteriosEspecificos() {
        // ARRANGE
        Usuario usuario = new Usuario();
        Mascota mascota = mock(Mascota.class);
        when(mascota.getTipoDeMascota()).thenReturn("Perro");
        when(mascota.getEdad()).thenReturn(5);
        when(mascota.getSexo()).thenReturn("Macho");
        usuario.setMascotas(List.of(mascota));

        List<Recomendacion> recomendacionesMock = List.of(
                new Recomendacion("Paseos", "Haz paseos largos", "Perro", "Adulto", "Ambos", null, null, null),
                new Recomendacion("Alimento", "Alimento premium", "Perro", "Adulto", "Ambos", null, null, null),
                new Recomendacion("Juguete", "Juguete para mordida", "Perro", "Adulto", "Macho", null, null, null)
        );
        when(repositorioRecomendacionMock.buscarPorCriterios("Perro", "Adulto", "Macho"))
                .thenReturn(recomendacionesMock);

        // ACT
        List<Recomendacion> resultado = servicioRecomendaciones.generarRecomendaciones(usuario);

        // ASSERT
        verify(repositorioRecomendacionMock).buscarPorCriterios("Perro", "Adulto", "Macho");
        assertEquals(3, resultado.size());
        assertEquals("Paseos", resultado.get(0).getTitulo());
    }


    @Test
    public void queDevuelvaLaListaVaciaSiElUsuarioNoTieneMascotas() {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setMascotas(new ArrayList<>()); // Lista vacía

        // ACT
        List<Recomendacion> resultado = servicioRecomendaciones.generarRecomendaciones(usuario);

        // ASSERT
        assertTrue(resultado.isEmpty());
    }





}
