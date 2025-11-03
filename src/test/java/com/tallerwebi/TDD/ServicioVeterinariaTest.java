package com.tallerwebi.TDD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.ServicioProfesionalImpl;
import com.tallerwebi.dominio.ServicioVeterinariaImpl;
import com.tallerwebi.dominio.Veterinaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

public class ServicioVeterinariaTest {
    
    private ServicioProfesionalImpl servicioProfesional;
    private ServicioVeterinariaImpl servicioVeterinaria;

    @BeforeEach
    public void setUp() {
        this.servicioProfesional = new ServicioProfesionalImpl();
        this.servicioVeterinaria = new ServicioVeterinariaImpl(servicioProfesional);
    }

    @Test
    public void queAlListarLasVeterinariasSeObtenganLasVeterinariasRegistradas() {
        //Vets, hardcodeadas:
        List<Veterinaria> veterinarias = servicioVeterinaria.listarVeterinarias();

        assertEquals(2, veterinarias.size());
        assertEquals("VetUno", veterinarias.get(0).getNombre());
        assertEquals("Vete Dos", veterinarias.get(1).getNombre());
    }

    @Test
    public void queAlBuscarUnaVeterinariaPorIdExistenteObtengaLaVeterinariaIndicada() {
        Veterinaria v = servicioVeterinaria.buscarPorId(1L);
        assertNotNull(v);
        assertEquals("VetUno", v.getNombre());
        assertEquals("Rivadavia 9128", v.getDireccion());
    }

    @Test
    public void queAlBuscarUnaVeterinariaPorUnIdNoRegistradoObtengaUnaVeterinariaVacia() {
        Veterinaria v = servicioVeterinaria.buscarPorId(999L);
        assertNotNull(v);
        assertNull(v.getNombre());
        assertNull(v.getDireccion());
    }
}
