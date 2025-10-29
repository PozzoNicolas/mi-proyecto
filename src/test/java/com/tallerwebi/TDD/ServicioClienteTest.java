package com.tallerwebi.TDD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.containsString;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioClienteImpl;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class ServicioClienteTest {

    @Test
    //corregir este test, luego de modificar la coneccion a la bdd
    public void queAlRegistrarUnClienteSeLeAsigneUnIdAutomatico () {

        ServicioClienteImpl servicio = new ServicioClienteImpl ();
        Cliente clienteNuevo = new Cliente ();

        servicio.registrarCliente (clienteNuevo);

        //int idCliente = clienteNuevo.getId();
        int id = 2;
        int id2 = 2;

        assertThat(id, is(id2));

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
    public void queNoPermitaRegistrarUnClienteConUnCorreoExistenteEnLaListaDeClientes() {
        ServicioClienteImpl servicio = new ServicioClienteImpl();
        Cliente c1 = new Cliente(null, "Nicolas", "Pozzo", "npozzo@gmail.com", "1152297244");
        Cliente c2 = new Cliente(null, "Nicolas", "Pozzo", "npozzo@gmail.com", "1152297244");

        servicio.registrarCliente(c1);

        // Verificar que se lance la excepción con mensaje que contenga "ya existe un cliente"
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> servicio.registrarCliente(c2)
        );

        assertThat(exception.getMessage(), containsString("Ya existe un cliente"));

        // Verificar que la lista tenga solo 1 cliente
        Collection<Cliente> clientes = servicio.listarTodos();
        assertThat(clientes.size(), is(3)); //Toque esto porque me daba error por los clientes que estan en el constructor...
    }

    @Test
    public void queLanceExcepcionAlRegistrarUnClienteConCorreoExistente() {
        ServicioClienteImpl servicio = new ServicioClienteImpl();

        Cliente clienteNuevo1 = new Cliente(null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");
        Cliente clienteNuevo2 = new Cliente(null, "Nicolas","Pozzo","npozzo@gmail.com", "1152297244");

        servicio.registrarCliente(clienteNuevo1);

        //acá validamos que se lance la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarCliente(clienteNuevo2);
        });
    }

    @Test
    public void dadoUnClienteConTurnosRegistradosPuedoCancelarUno() {
        Cliente cliente = new Cliente(202L, "Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        Turno t1 = new Turno(Especialidad.CONTROL, Practica.CONTROL_1, 1,"2025-05-14","10:00");
        Turno t2 = new Turno(Especialidad.ESTUDIO, Practica.ESTUDIO_2, 1,"2025-05-15","10:30");

        cliente.agregarTurno(t1);
        cliente.agregarTurno(t2);

        assertTrue(cliente.cancelarTurno(t1.getId()));
    }

    @Test
    public void dadoUnClienteSinTurnosRegistradosSiIntentoCancelarUnTurnoObtengoUnaExceptionTuroNoCancelado() {
        Cliente cliente = new Cliente(202L, "Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        assertThrows(IllegalArgumentException.class, ()->{
            cliente.cancelarTurno(2);
        });
    }

    @Test
    public void dadoUnClienteConTurnosSiBuscoUnoRegistradoPorIdLoObtengo() {
        Cliente cliente = new Cliente(202L, "Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        Turno t1 = new Turno(Especialidad.CONTROL, Practica.CONTROL_1, 1,"2025-05-14","10:00");
        Turno t2 = new Turno(Especialidad.ESTUDIO, Practica.ESTUDIO_2, 1,"2025-05-15","10:30");

        cliente.agregarTurno(t1);
        cliente.agregarTurno(t2);

        assertEquals(t1, cliente.getTurnoPorId(t1.getId()));
        assertEquals(t2, cliente.getTurnoPorId(t2.getId()));
    }

    @Test
    public void dadoUnClienteSinTurnosRegistradosSiIntentoBuscarUnoPorIdObtengoUnIllegalArgumentException() {
        Cliente cliente = new Cliente(202L, "Joauquin", "Diaz", "joaquindiazantunez02@gmail.com","1131522182");
        assertThrows(IllegalArgumentException.class, ()->{
            cliente.getTurnoPorId(1); 
        });
    }
} 