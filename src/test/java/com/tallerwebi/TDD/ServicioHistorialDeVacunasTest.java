package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioHistorialDeVacunasTest {

    private RepositorioHistorialDeVacunas repositorioMock;
    private ServicioHistorialDeVacunasImpl servicio;

    private Mascota mascota;
    private Usuario usuario;
    private HistorialDeVacunas historial;

    @BeforeEach
    public void init() {
        repositorioMock = Mockito.mock(RepositorioHistorialDeVacunas.class);
        servicio = new ServicioHistorialDeVacunasImpl(repositorioMock);

        usuario = new Usuario();
        usuario.setNombre("pepe");
        mascota = new Mascota();
        mascota.setNombre("Firulais");
        mascota.setTipoDeMascota("Perro");
        mascota.setDuenio(usuario);

        historial = new HistorialDeVacunas(mascota);
    }

    @Test
    public void queSePuedaCrearUnHistorialDeVacunas() {
        HistorialDeVacunasDTO dto = new HistorialDeVacunasDTO();
        dto.setMascota(mascota);
        dto.setVacunaciones(List.of());

        servicio.crearUnHistorialDeVacunas(dto);

        verify(repositorioMock, times(1)).guardar(any(HistorialDeVacunas.class));
    }

    @Test
    public void queSePuedaObtenerElHistorialDeUnaMascota() {
        when(repositorioMock.buscarPorMascota(mascota)).thenReturn(historial);

        HistorialDeVacunas resultado = servicio.getHistorialDeUnaMascota(mascota);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getMascota().getNombre());
        verify(repositorioMock, times(1)).buscarPorMascota(mascota);
    }

    @Test
    public void queListeLosHistorialesDeUnUsuario() {
        when(repositorioMock.listarPorUsuario(usuario)).thenReturn(List.of(historial));

        List<HistorialDeVacunas> historiales = servicio.listarTodosLosHistorialesDeUnUsuario(usuario);

        assertEquals(1, historiales.size());
        assertEquals("Firulais", historiales.get(0).getMascota().getNombre());
    }
}
