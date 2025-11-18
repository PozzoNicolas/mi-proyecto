package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServicioMascotaImpl implements ServicioMascota {

    private final ServicioUsuario servicioUsuario;
    private final RepositorioMascota repositorioMascota;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioMascotaImpl(RepositorioMascota repositorioMascota, ServicioUsuario servicioUsuario,
            RepositorioUsuario repositorioUsuario) {
        this.repositorioMascota = repositorioMascota;
        this.servicioUsuario = servicioUsuario;
        this.repositorioUsuario = repositorioUsuario;
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

        if (mascota != null) {
            if (mascota.getDuenio() != null) {
                Usuario duenio = mascota.getDuenio();

                // 1. Romper la relación en Java (eliminar el huérfano)
                duenio.getMascotas().remove(mascota);

                // 2. Persistir el Dueño
                // Esto forzará al Dueño a actualizarse, y orphanRemoval=true se encargará de
                // generar la sentencia DELETE para la Mascota.
                repositorioUsuario.guardar(duenio);

                // 🔥 NO LLAMES MÁS A ESTA FUNCIÓN: ¡OrphanRemoval lo hace por ti!
                // repositorioMascota.eliminarMascota(mascota);

            } else {
                // Si no tiene dueño, la eliminas directamente (esto no debería ocurrir)
                repositorioMascota.eliminarMascota(mascota);
            }
        }
    }

}
