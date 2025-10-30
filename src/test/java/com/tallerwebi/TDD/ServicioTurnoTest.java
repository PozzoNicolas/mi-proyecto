package com.tallerwebi.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class ServicioTurnoTest {
    
    private ServicioProfesionalImpl servicioProfesional;
    private ServicioVeterinariaImpl servicioVeterinaria;
    private ServicioTurnosImpl servicioTurnos;

    @BeforeEach
    public void setUp() {
        this.servicioProfesional = new ServicioProfesionalImpl();
        this.servicioVeterinaria = new ServicioVeterinariaImpl(servicioProfesional);
        this.servicioTurnos = new ServicioTurnosImpl(servicioVeterinaria); 
    }

    @Test
    public void devolverTrueCuandoLaBusquedaDeUnTurnoSeaValida() {
        Turno turno = new Turno();

        turno.setEspecialidad(Especialidad.ESTUDIO);
        turno.setPractica(Practica.ESTUDIO_1);
        assertTrue(servicioTurnos.esTurnoValido(turno)); 
    }

    @Test
    public void devolverFalseAlBuscarUnTurnoSinEspecialidad() {
        Turno turno = new Turno();
        turno.setPractica(Practica.VACUNA_1);
        assertFalse(servicioTurnos.esTurnoValido(turno)); 
    }

    @Test 
    public void devolverFalseAlBuscarUnTurnoSinPractica() {
        Turno turno = new Turno();
        turno.setEspecialidad(Especialidad.ESTUDIO);
        assertFalse(servicioTurnos.esTurnoValido(turno));
    }

    @Test 
    public void queAlListarVeterinariasConUnaBusquedaIndiferenteObtengoUnaListaDeTodasLasVeterinariasRegistradas() {
        List<Veterinaria> veterinariasEsperadas = servicioVeterinaria.listarVeterinarias();
        List<Veterinaria> veterinariasBuscadas = servicioTurnos.listarVeterinariasIndiferente();

        int i = 0;
        while (i < veterinariasBuscadas.size()) {
            Veterinaria esperada = veterinariasEsperadas.get(i);
            Veterinaria buscada = veterinariasBuscadas.get(i);

            assertEquals(esperada.getNombre(), buscada.getNombre());
            assertEquals(esperada.getDireccion(), buscada.getDireccion());
            i++;
        }
    }

    @Test
    public void queAlHacerUnaBusquedaConUnaVeterinariaEspecificaLaMismaSeRetorne() {
        Turno turno = new Turno(); 
        turno.setVeterinaria(1);
        Veterinaria v = servicioTurnos.obtenerVeterinariaPorTurno(turno);
        assertEquals(servicioVeterinaria.buscarPorId(1), v);
    }

    @Test
    public void queAlHacerUnaBusquedaSinUnaVeterinariaEspecificaObtengaUnObjetoVeterinariaVacio() {
        Turno turno = new Turno(); 
        turno.setVeterinaria(0);
        Veterinaria v = servicioTurnos.obtenerVeterinariaPorTurno(turno);

        assertNotNull(v);
        assertNull(v.getNombre());
        assertNull(v.getDireccion());
        assertTrue(v.getProfesionalesEnHorario() == null || v.getProfesionalesEnHorario().isEmpty());
    }


    @Test
    public void queElServicioSeaCapazDeGuardarElTurnoEnUnUsuarioDado() {
        Usuario usuario = new Usuario();

        Turno turno = new Turno();
        turno.setHorario("10:00");

        servicioTurnos.guardarTurno(usuario, turno);

        assertEquals(1, usuario.getTurnos().size());
        assertEquals("10:00", usuario.getTurnos().get(0).getHorario());
    }

    @Test
    public void queElServicioSeaCapazDeProcesarSeleccionConTresPartes() {
        Turno turno = new Turno();
        turno.setSeleccion("1||10:00||Dr. Smith");

        servicioTurnos.procesarSeleccion(turno);

        assertEquals(1, turno.getVeterinaria());
        assertEquals("10:00", turno.getHorario());
        assertEquals("Dr. Smith", turno.getProfesional());
    }
    
}
