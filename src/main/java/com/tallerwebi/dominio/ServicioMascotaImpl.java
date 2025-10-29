package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServicioMascotaImpl implements ServicioMascota {

    private final ServicioCliente servicioCliente;
    private final RepositorioMascota repositorioMascota;

    @Autowired
    public ServicioMascotaImpl(RepositorioMascota repositorioMascota, ServicioCliente servicioCliente) {
        this.repositorioMascota = repositorioMascota;
        this.servicioCliente = servicioCliente;
    }

    @Override
    @Transactional
    public void registrarMascota(Long id, Mascota mascota) {

        Cliente cliente = servicioCliente.buscarClientePorId(id);
        if (cliente != null) {
            cliente.agregarMascota(mascota);
            mascota.setDuenio(cliente);
            // Guardo la mascota en la base de datos
            repositorioMascota.guardar(mascota);
            //prueba para ver si funciona el guardado en la bdd
            System.out.println("Mascota guardada: " + mascota.getNombre());
        }
    }
}
