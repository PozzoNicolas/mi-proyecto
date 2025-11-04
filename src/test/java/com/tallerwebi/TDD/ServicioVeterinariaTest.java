package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

public class ServicioVeterinariaTest {
    
    private ServicioProfesionalImpl servicioProfesional;
    private ServicioVeterinariaImpl servicioVeterinaria;
    @Mock
    private RepositorioProfesional repositorioProfesional;
    @Mock
    private RepositorioVeterinaria repositorioVeterinaria;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.servicioProfesional = new ServicioProfesionalImpl(repositorioProfesional);
        this.servicioVeterinaria = new ServicioVeterinariaImpl(repositorioVeterinaria,repositorioProfesional);
    }

    @Test
    public void queAlListarLasVeterinariasSeObtenganLasVeterinariasRegistradas() {

        List<Veterinaria> listaMock = List.of(
                new Veterinaria("Vet Uno", "Calle Falsa 123"),
                new Veterinaria("Vet Dos", "Juan B. Justo")
        );

        when(repositorioVeterinaria.listarVeterinarias()).thenReturn(listaMock);

        List<Veterinaria> veterinarias = servicioVeterinaria.listarVeterinarias();

        assertEquals(2, veterinarias.size());
        assertEquals("Vet Uno", veterinarias.get(0).getNombre());
        assertEquals("Vet Dos", veterinarias.get(1).getNombre());
    }

    @Test
    public void queAlBuscarUnaVeterinariaPorIdExistenteObtengaLaVeterinariaIndicada() {
        Veterinaria vMock = new Veterinaria("Vet Uno", "Rivadavia 9128");
        vMock.setId(1L);

        when(repositorioVeterinaria.buscarPorId(1L)).thenReturn(vMock);

        Veterinaria v = servicioVeterinaria.buscarPorId(1L);

        assertNotNull(v);
        assertEquals("Vet Uno", v.getNombre());
        assertEquals("Rivadavia 9128", v.getDireccion());
    }

    @Test
    public void queAlBuscarUnaVeterinariaPorUnIdNoRegistradoObtengaUnaVeterinariaVacia() {
        when(repositorioVeterinaria.buscarPorId(999L)).thenReturn(null);

        Veterinaria v = servicioVeterinaria.buscarPorId(999L);

        assertNotNull(v);
        assertNull(v.getNombre());
        assertNull(v.getDireccion());
    }
}
