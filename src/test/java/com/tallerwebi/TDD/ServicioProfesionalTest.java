package com.tallerwebi.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.ServicioProfesionalImpl;

public class ServicioProfesionalTest {

    private ServicioProfesionalImpl servicioProfesional;

    @BeforeEach
    public void setUp() {
        this.servicioProfesional = new ServicioProfesionalImpl(); 
    }

    @Test
    public void siBuscoUnProfesionalPorSuDniRegistradoObtengoAlProfesionalAdecuado() {
        Profesional p = servicioProfesional.buscarPorDni(111);
        assertEquals("Ernesto Sabato", p.getNombre());
    }
}
