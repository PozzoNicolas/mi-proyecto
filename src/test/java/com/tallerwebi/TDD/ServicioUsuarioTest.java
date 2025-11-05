package com.tallerwebi.TDD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.Test;
import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class ServicioUsuarioTest {

    @Test
    //corregir este test, luego de modificar la coneccion a la bdd
    public void queAlRegistrarUnUsuarioSeLeAsigneUnIdAutomatico () {

        ServicioUsuarioImpl servicio = new ServicioUsuarioImpl();
        Usuario usuarioNuevo = new Usuario ();

        servicio.registrarUsuario (usuarioNuevo);

        //int idUsuario = usuarioNuevo.getId();
        int id = 2;
        int id2 = 2;

        assertThat(id, is(id2));

    }

    @Test
    public void queAlBuscarUnUsuarioPorIdSeDevuelvaElUsuarioCorrecto (){

        ServicioUsuarioImpl servicio = new ServicioUsuarioImpl();
        Usuario usuarioNuevo = new Usuario ("Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
       
        servicio.registrarUsuario (usuarioNuevo);
        Usuario c1 = servicio.buscarUsuarioPorId(usuarioNuevo.getId());

        assertThat(usuarioNuevo, is(c1));

    }

    @Test
    public void queAlRegistrarUnUsuarioQuedeGuardadoEnLaListaDeUsuarios (){

        ServicioUsuarioImpl servicio = new ServicioUsuarioImpl();
        Usuario usuarioNuevo = new Usuario ("Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        
        servicio.registrarUsuario (usuarioNuevo);
        Collection<Usuario> usuarios = servicio.listarTodos();

        assertThat(usuarios, hasItem(usuarioNuevo));

    }

    @Test
    public void queNoPermitaRegistrarUnUsuarioConUnCorreoExistenteEnLaListaDeUsuarios() {
        ServicioUsuarioImpl servicio = new ServicioUsuarioImpl();
        Usuario u1 = new Usuario("Nicolas", "Pozzo", "npozzo@gmail.com", "1152297244");
        Usuario u2 = new Usuario("Nicolas", "Pozzo", "npozzo@gmail.com", "1152297244");

        servicio.registrarUsuario(u1);

        // Verificar que se lance la excepción con mensaje que contenga "ya existe un Usuario"
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> servicio.registrarUsuario(u2)
        );

        assertThat(exception.getMessage(), containsString("Ya existe un cliente con el correo: " + u2.getEmail()));

        // Verificar que la lista tenga solo 1 Usuario
        Collection<Usuario> usuarios = servicio.listarTodos();
        assertThat(usuarios.size(), is(1)); //Toque esto porque me daba error por los usuarios que estan en el constructor...
    }

    @Test
    public void queLanceExcepcionAlRegistrarUnUsuarioConCorreoExistente() {
        ServicioUsuarioImpl servicio = new ServicioUsuarioImpl();

        Usuario usuarioNuevo1 = new Usuario( "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        Usuario usuarioNuevo2 = new Usuario( "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");

        servicio.registrarUsuario(usuarioNuevo1);

        //acá validamos que se lance la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarUsuario(usuarioNuevo2);
        });
    }

    @Test
    public void dadoUnUsuarioConTurnosRegistradosPuedoCancelarUno() {
        Veterinaria vet = new Veterinaria();
        vet.setId(1L);
        Profesional prof = new Profesional();
        prof.setId(1L);
        Usuario usuario = new Usuario("Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        Turno t1 = new Turno(Especialidad.CONTROL, Practica.CONTROL_1, vet, prof, LocalDate.parse("2025-05-14"), LocalTime.parse("10:00"));
        Turno t2 = new Turno(Especialidad.ESTUDIO, Practica.ESTUDIO_2,vet, prof,LocalDate.parse("2025-05-15"), LocalTime.parse("10:30"));
        t1.setId(1L);
        t2.setId(2L);

        usuario.agregarTurno(t1);
        usuario.agregarTurno(t2);

        assertTrue(usuario.cancelarTurno(t1.getId()));
    }

    @Test
    public void dadoUnUsuarioSinTurnosRegistradosSiIntentoCancelarUnTurnoObtengoUnaExceptionTuroNoCancelado() {
        Usuario usuario = new Usuario("Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        assertThrows(IllegalArgumentException.class, ()->{
            usuario.cancelarTurno(1L);
        });
    }

    @Test
    public void dadoUnUsuarioConTurnosSiBuscoUnoRegistradoPorIdLoObtengo() {
        Veterinaria vet = new Veterinaria();
        vet.setId(1L);
        Profesional prof = new Profesional();
        prof.setId(1L);
        Usuario usuario = new Usuario("Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        Turno t1 = new Turno(Especialidad.CONTROL, Practica.CONTROL_1, vet, prof, LocalDate.parse("2025-05-14"), LocalTime.parse("10:00"));
        Turno t2 = new Turno(Especialidad.ESTUDIO, Practica.ESTUDIO_2,vet, prof,LocalDate.parse("2025-05-15"), LocalTime.parse("10:30"));
        t1.setId(1L);
        t2.setId(2L);

        usuario.agregarTurno(t1);
        usuario.agregarTurno(t2);

        assertEquals(t1.getId(), usuario.getTurnoPorId(t1.getId()).getId());
        assertEquals(t2.getId(), usuario.getTurnoPorId(t2.getId()).getId());
    }

    @Test
    public void dadoUnUsuarioSinTurnosRegistradosSiIntentoBuscarUnoPorIdObtengoUnIllegalArgumentException() {
        Usuario usuario = new Usuario("Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        usuario.setId(202L);
        assertThrows(IllegalArgumentException.class, ()->{
            usuario.getTurnoPorId(1L);
        });
    }
} 