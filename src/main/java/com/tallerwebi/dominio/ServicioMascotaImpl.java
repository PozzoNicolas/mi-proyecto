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

// En ServicioMascotaImpl

    @Override
    @Transactional
    public void registrarMascota(Long id, Mascota mascota) {
        System.out.println("Buscando usuario con ID: " + id);
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
        System.out.println("Resultado de búsqueda: " + usuario);

        if (usuario == null) {
            throw new RuntimeException("Dueño no encontrado para registrar mascota.");
        }

        usuario.agregarMascota(mascota);
        mascota.setDuenio(usuario);
        repositorioMascota.guardar(mascota);
        System.out.println("Mascota guardada: " + mascota.getNombre());
    }

    @Override
    @Transactional // 🔥 CRÍTICO: Abre la transacción para la operación de base de datos
    public void eliminarMascota(Long idMascota) {

        Mascota mascota = repositorioMascota.buscarMascotaPorId(idMascota);

        if (mascota != null && mascota.getDuenio() != null) {
            Usuario duenio = mascota.getDuenio();
            duenio.getMascotas().remove(mascota);
            mascota.setDuenio(null);
        }

        repositorioMascota.eliminarMascota(mascota);



    }
}


