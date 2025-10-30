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
    }

//        Usuario duenio = servicioUsuario.buscarUsuarioPorId(id);
//        if (duenio == null) {
//            throw new RuntimeException("Dueño no encontrado para registrar mascota.");
//        }
//        mascota.setDuenio(duenio);
//        duenio.getMascotas().add(mascota);
//
//        System.out.println("Mascota guardada: " + mascota.getNombre());
//    }
//}
