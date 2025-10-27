package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ServicioMascotaTest {

    @Test
    public void queAlRegistrarUnaMascotaQuedeAsociadaAlCliente(){

        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioClienteImpl servicioCliente = new ServicioClienteImpl();

        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota ,servicioCliente);

        Mascota mascota = new Mascota();
        Cliente cliente = new Cliente(2L,"nicolas", "Pozzo", "npozzo@gmail.com", "11223344");
        servicioCliente.registrarCliente(cliente);

        servicioMascota.registrarMascota (cliente.getId(), mascota);

        assertTrue(cliente.getMascotas().contains(mascota));
        assertEquals(1, cliente.getMascotas().size());
    }

    @Test
    public void queAlConsultarElDuenioDeLaMascotaTeDevuelvaElDuenioReal(){
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioClienteImpl servicioCliente = new ServicioClienteImpl();
        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioCliente);

        Mascota mascota = new Mascota();
        Cliente cliente = new Cliente(2L,"nicolas", "Pozzo", "npozzo@gmail.com", "11223344");

        servicioCliente.registrarCliente(cliente);
        servicioMascota.registrarMascota (cliente.getId(), mascota);
        Cliente duenioObtenido = mascota.getDuenio();

        assertThat(duenioObtenido, is(cliente));
    }

    @Test
    public void queAlRegistrarDosMascotasQuedenAmbasEnLaListaDelCliente(){
        RepositorioMascota repositorioMascota = mock(RepositorioMascota.class);
        ServicioClienteImpl servicioCliente = new ServicioClienteImpl();
        ServicioMascotaImpl servicioMascota = new ServicioMascotaImpl(repositorioMascota, servicioCliente);

        Mascota m1 = new Mascota();
        Mascota m2 = new Mascota();
        Cliente cliente = new Cliente();

        servicioCliente.registrarCliente(cliente);
        servicioMascota.registrarMascota(cliente.getId(),m1);
        servicioMascota.registrarMascota(cliente.getId(),m2);

        Integer cantidadDeMascotasDelCliente = cliente.getMascotas().size();
        Integer cantidadDeMascotasEsperadas = 2;

        assertThat(cantidadDeMascotasDelCliente, is(cantidadDeMascotasEsperadas));
    }







}
