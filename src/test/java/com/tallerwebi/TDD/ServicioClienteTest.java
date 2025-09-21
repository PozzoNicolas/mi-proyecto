package com.tallerwebi.TDD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioClienteImpl;

public class ServicioClienteTest {

    @Test
    public void queAlRegistrarUnClienteSeLeAsigneUnIdAutomatico () {

        ServicioClienteImpl servicio = new ServicioClienteImpl ();
        Cliente clienteNuevo = new Cliente ();

        servicio.registrarCliente (clienteNuevo);

        int idCliente = clienteNuevo.getId();
        int id = 1;
        assertThat(idCliente, is(id));
    }

    @Test
    public void queAlBuscarUnClientePorIdSeDevuelvaElClienteCorrecto (){

        ServicioClienteImpl servicio = new ServicioClienteImpl ();
        Cliente clienteNuevo = new Cliente (null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
       
        servicio.registrarCliente (clienteNuevo);
        Cliente c1 = servicio.buscarClientePorId(clienteNuevo.getId());

        assertThat(clienteNuevo, is(c1));

    }

    @Test
    public void queAlRegistrarUnClienteQuedeGuardadoEnLaListaDeClientes (){

        ServicioClienteImpl servicio = new ServicioClienteImpl ();
        Cliente clienteNuevo = new Cliente (null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        
        servicio.registrarCliente (clienteNuevo);
        Collection<Cliente> clientes = servicio.listarTodos();

        assertThat(clientes, hasItem(clienteNuevo));

    }

    @Test
    public void queNoPermitaRegistrarUnClienteConUnCorreoExistenteEnLaListaDeClientes (){

        ServicioClienteImpl servicio = new ServicioClienteImpl ();
        Cliente c1 = new Cliente (null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        Cliente c2 = new Cliente (null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");

        servicio.registrarCliente (c1);
        try {
            servicio.registrarCliente(c2);
        } catch (IllegalArgumentException e) {
            // ok, lanzó la excepción esperada
        }

        Collection<Cliente> clientes = servicio.listarTodos();
        int largoDeLista = 3;
        int largoDeClientes = clientes.size();

        assertThat(largoDeClientes, is(largoDeLista));

    }

    @Test
    public void queLanceExcepcionAlRegistrarUnClienteConCorreoExistente() {
        ServicioClienteImpl servicio = new ServicioClienteImpl();

        Cliente clienteNuevo1 = new Cliente(null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        Cliente clienteNuevo2 = new Cliente(null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");

        servicio.registrarCliente(clienteNuevo1);

        // 🚀 acá validamos que se lance la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarCliente(clienteNuevo2);
        });
    }




}
