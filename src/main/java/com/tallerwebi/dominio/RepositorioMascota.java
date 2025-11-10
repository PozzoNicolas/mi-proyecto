package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMascota {

    void guardar(Mascota mascota);

    Mascota buscarMascotaPorId(Long id);

    void eliminarMascota(Mascota mascota);

    List<Mascota> listarMascotas();

    void eliminar(Mascota mascota);
}
