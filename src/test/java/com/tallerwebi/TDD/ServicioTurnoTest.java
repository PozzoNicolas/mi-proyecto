package com.tallerwebi.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioTurnosImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class ServicioTurnoTest {
    
    private ServicioProfesionalImpl servicioProfesional;
    private ServicioVeterinariaImpl servicioVeterinaria;
    private ServicioTurnosImpl servicioTurnos;
    private RepositorioTurnosImpl respositorioTurnos;
    private ServicioMail servicioMail;

    @BeforeEach
    public void setUp() {
        this.respositorioTurnos = mock(RepositorioTurnosImpl.class);
        this.servicioProfesional = mock(ServicioProfesionalImpl.class);
        this.servicioVeterinaria = mock(ServicioVeterinariaImpl.class);
        this.servicioMail = mock(ServicioMail.class);
        this.servicioTurnos = new ServicioTurnosImpl(servicioVeterinaria, respositorioTurnos, servicioMail, servicioProfesional);
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

        Veterinaria veterinariaEsperada = new Veterinaria("Vet Uno", "Calle 123");
        veterinariaEsperada.setId(1L);

        Turno turno = new Turno();
        turno.setVeterinaria(veterinariaEsperada);

        when(servicioVeterinaria.buscarPorId(1L))
                .thenReturn(veterinariaEsperada);

        Veterinaria veterinariaObtenida = servicioTurnos.obtenerVeterinariaPorTurno(turno);

        assertEquals(veterinariaEsperada, veterinariaObtenida);
    }

    @Test
    public void queAlHacerUnaBusquedaSinUnaVeterinariaEspecificaObtengaUnObjetoVeterinariaVacio() {

        Turno turno = new Turno(); 
        turno.setVeterinaria(null);
        Veterinaria v = servicioTurnos.obtenerVeterinariaPorTurno(turno);

        assertNotNull(v);
        assertNull(v.getNombre());
        assertNull(v.getDireccion());
        assertTrue(v.getProfesionalesEnHorario() == null || v.getProfesionalesEnHorario().isEmpty());
    }


    @Test
    public void queElServicioSeaCapazDeGuardarElTurnoEnUnUsuarioDado() {
        Usuario usuario = new Usuario("Nicolas", "Pozzo","nico@gmail.com","1155225522");

        Turno turno = new Turno();
        turno.setHorario(LocalTime.parse("10:00"));

        servicioTurnos.guardarTurno(usuario, turno);

        assertEquals(1, usuario.getTurnos().size());
        assertEquals(LocalTime.parse("10:00"), usuario.getTurnos().get(0).getHorario());
    }

    @Test
    public void queElServicioSeaCapazDeProcesarSeleccionConTresPartes() {
        Turno turno = new Turno();
        turno.setSeleccion("1||10:00||222");

        Veterinaria vet = new Veterinaria();
        vet.setId(1L);

        Profesional prof = new Profesional();
        prof.setDni(222);

        when(servicioVeterinaria.buscarPorId(1L)).thenReturn(vet);
        when(servicioProfesional.buscarPorDni(222)).thenReturn(prof);

        servicioTurnos.procesarSeleccion(turno);

        assertEquals(1L, turno.getVeterinaria().getId());
        assertEquals(LocalTime.parse("10:00"), turno.getHorario());
        assertEquals(222, turno.getProfesional().getDni());
    }
    
}
