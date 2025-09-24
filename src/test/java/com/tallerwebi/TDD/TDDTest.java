package com.tallerwebi.TDD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tallerwebi.dominio.Turno;

public class TDDTest {

    @Test
    public void debeDevolverINVALIDA_cuandoLaContraseñaTieneMenosde8Caracteres() {

        TDD obj = new TDD();
        String contraseñaDe3Caracteres = "abc";
        String fortalezaDeLaContraseña = obj.validarFortaleza(contraseñaDe3Caracteres);

        assertThat(fortalezaDeLaContraseña, equalToIgnoringCase("INVALIDA"));
    }

    @Test
    public void debeDevolverDEBIL_cuandoLaContraseñaTiene8Caracteres() {

        TDD obj = new TDD();
        String contraseñaDe8Caracteres = "abcdefgh";
        String fortalezaDeLaContraseña = obj.validarFortaleza(contraseñaDe8Caracteres);

        assertThat(fortalezaDeLaContraseña, equalToIgnoringCase("DEBIL"));
    }

    @Test
    public void debeDevolverMEDIANA_cuandoLaContraseñaTiene4CaracteresYUnArrobaEspecial() {

        TDD obj = new TDD();
        String contraseñaDe8Caracteres = "abc@";
        String fortalezaDeLaContraseña = obj.validarFortaleza(contraseñaDe8Caracteres);

        assertThat(fortalezaDeLaContraseña, equalToIgnoringCase("MEDIANA"));
    }

    @Test
    public void debeDevolverFUERTE_cuandoLaContraseñaTiene4NumerosUnArrobay3Letras(){
        TDD obj = new TDD();
        String contraseñaDe8Caracteres = "123p8pp@abc";
        String fortalezaDeLaContraseña = obj.validarFortaleza(contraseñaDe8Caracteres);

        assertThat(fortalezaDeLaContraseña, equalToIgnoringCase("FUERTE"));
    }

    @Test
    public void testTurnoGetters() {
        Turno turno = new Turno(1, "vacuna", "Rabia", "consultorio 1", "2025-11-12", "10:00");
        assertEquals(Integer.toString(1), Integer.toString(turno.getId()));
        assertEquals("vacuna", turno.getEspecialidad());
        assertEquals("Rabia", turno.getPractica());
        assertEquals("consultorio", turno.getLugar());
        assertEquals("2025-11-12", turno.getFecha());
        assertEquals("10:00", turno.getHorario());
    }

    @Test
    public void testTrunoSetters() {
        Turno turno = new Turno(); 

        turno.setId(1);
        turno.setEspecialidad("vacuna");
        turno.setPractica("Rabia");
        turno.setLugar("consultorio");
        turno.setFecha("2025-11-12");
        turno.setHorario("11:30");

        assertEquals(Integer.toString(1), Integer.toString(turno.getId()));
        assertEquals("vacuna", turno.getEspecialidad());
        assertEquals("Rabia", turno.getPractica());
        assertEquals("consultorio", turno.getLugar());
        assertEquals("2025-11-12", turno.getFecha());
        assertEquals("11:30", turno.getHorario());
    }
}
