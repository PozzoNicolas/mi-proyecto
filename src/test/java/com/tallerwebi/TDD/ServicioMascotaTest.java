package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.RepositorioMascota;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioMascotaTest {

    @Test
    public void queAlRegistrarUnaMascotaQuedeAsociadaAlUsuario() {

        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioUsuarioImpl servicioUsuario = mock(ServicioUsuarioImpl.class);

        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota ,servicioUsuario, repositorioUsuario);

        Mascota mascota = new Mascota();
        Usuario usuario = new Usuario("nicolas", "Pozzo", "npozzo@gmail.com", "11223344");

        when(servicioUsuario.buscarUsuarioPorId(usuario.getId())).thenReturn(usuario);
        servicioMascota.registrarMascota (usuario.getId(), mascota);

        assertTrue(usuario.getMascotas().contains(mascota));
        assertEquals(1, usuario.getMascotas().size());
    }

    @Test
    public void queAlConsultarElDuenioDeLaMascotaTeDevuelvaElDuenioReal(){
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioUsuarioImpl servicioUsuario = mock(ServicioUsuarioImpl.class);

        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioUsuario, repositorioUsuario);

        Mascota mascota = new Mascota();
        Usuario usuario = new Usuario("nicolas", "Pozzo", "npozzo@gmail.com", "11223344");

        when(servicioUsuario.buscarUsuarioPorId(usuario.getId())).thenReturn(usuario);
        servicioMascota.registrarMascota (usuario.getId(), mascota);
        mascota.setDuenio(usuario);
        Usuario duenioObtenido = mascota.getDuenio();

        assertThat(duenioObtenido, is(usuario));
    }

    @Test
    public void queAlRegistrarDosMascotasQuedenAmbasEnLaListaDelUsuario() {
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioUsuarioImpl servicioUsuario = mock (ServicioUsuarioImpl.class);

        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioUsuario, repositorioUsuario);

        Mascota m1 = new Mascota();
        Mascota m2 = new Mascota();
        Usuario usuario = new Usuario();

        when(servicioUsuario.buscarUsuarioPorId(usuario.getId())).thenReturn(usuario);

        servicioMascota.registrarMascota(usuario.getId(),m1);
        servicioMascota.registrarMascota(usuario.getId(),m2);
        Integer cantidadDeMascotasDelUsuario = usuario.getMascotas().size();
        Integer cantidadDeMascotasEsperadas = 2;

        assertThat(cantidadDeMascotasDelUsuario, is(cantidadDeMascotasEsperadas));
    }
}
