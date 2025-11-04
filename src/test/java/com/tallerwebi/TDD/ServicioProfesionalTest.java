package com.tallerwebi.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.RepositorioProfesional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.ServicioProfesionalImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ServicioProfesionalTest {

    private ServicioProfesionalImpl servicioProfesional;
    @Mock
    private RepositorioProfesional repositorioProfesional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servicioProfesional = new ServicioProfesionalImpl(repositorioProfesional);
    }

    @Test
    public void siBuscoUnProfesionalPorSuDniRegistradoObtengoAlProfesionalAdecuado() {

        Profesional pMock = new Profesional("Ernesto Sabato", 111);

        when(repositorioProfesional.buscarPorDni(111)).thenReturn(pMock);

        Profesional p = servicioProfesional.buscarPorDni(111);

        assertNotNull(p);
        assertEquals("Ernesto Sabato", p.getNombre());
        assertEquals(111, p.getDni());
    }
}
