package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioMascotaImpl implements ServicioMascota {

    private final ServicioCliente servicioCliente;

    @Autowired
    public ServicioMascotaImpl(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @Override
    public void registrarMascota(Integer id, Mascota mascota) {

        Cliente cliente = servicioCliente.buscarClientePorId(id);
        if (cliente != null) {
            cliente.agregarMascota(mascota);
            mascota.setDuenio(cliente);
        }

    }
}
