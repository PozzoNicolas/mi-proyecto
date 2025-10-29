package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServicioMascotaImpl implements ServicioMascota {

    private final ServicioUsuario servicioUsuario;
    private final RepositorioMascota repositorioMascota;

    @Autowired
    public ServicioMascotaImpl(RepositorioMascota repositorioMascota, ServicioUsuario servicioUsuario) {
        this.repositorioMascota = repositorioMascota;
        this.servicioUsuario = servicioUsuario;
    }

    @Override
    @Transactional
    public void registrarMascota(Long id, Mascota mascota) {

        Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
        if (usuario != null) {
            usuario.agregarMascota(mascota);
            mascota.setDuenio(usuario);
            // Guardo la mascota en la base de datos
            repositorioMascota.guardar(mascota);
            //prueba para ver si funciona el guardado en la bdd
            System.out.println("Mascota guardada: " + mascota.getNombre());
        }
    }
}
