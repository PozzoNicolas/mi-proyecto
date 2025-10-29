package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ServicioMascotaTest {

    @Test
    public void queAlRegistrarUnaMascotaQuedeAsociadaAlUsuario() {

        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioUsuarioImpl servicioUsuario = new ServicioUsuarioImpl();

        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota ,servicioUsuario);

        Mascota mascota = new Mascota();
        Usuario usuario = new Usuario("nicolas", "Pozzo", "npozzo@gmail.com", "11223344");
        servicioUsuario.registrarUsuario(usuario);

        servicioMascota.registrarMascota (usuario.getId(), mascota);

        assertTrue(usuario.getMascotas().contains(mascota));
        assertEquals(1, usuario.getMascotas().size());
    }

    @Test
    public void queAlConsultarElDuenioDeLaMascotaTeDevuelvaElDuenioReal(){
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioUsuarioImpl servicioUsuario = new ServicioUsuarioImpl();
        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioUsuario);

        Mascota mascota = new Mascota();
        Usuario usuario = new Usuario("nicolas", "Pozzo", "npozzo@gmail.com", "11223344");

        servicioUsuario.registrarUsuario(usuario);
        servicioMascota.registrarMascota (usuario.getId(), mascota);
        Usuario duenioObtenido = mascota.getDuenio();

        assertThat(duenioObtenido, is(usuario));
    }

    @Test
    public void queAlRegistrarDosMascotasQuedenAmbasEnLaListaDelUsuario() {
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioUsuarioImpl servicioUsuario = new ServicioUsuarioImpl();
        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioUsuario);

        Mascota m1 = new Mascota();
        Mascota m2 = new Mascota();
        Usuario usuario = new Usuario();

        servicioUsuario.registrarUsuario(usuario);
        servicioMascota.registrarMascota(usuario.getId(),m1);
        servicioMascota.registrarMascota(usuario.getId(),m2);

        Integer cantidadDeMascotasDelUsuario = usuario.getMascotas().size();
        Integer cantidadDeMascotasEsperadas = 2;

        assertThat(cantidadDeMascotasDelUsuario, is(cantidadDeMascotasEsperadas));
    }







}
