package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioHistorialDeVacunas {
    void guardar(HistorialDeVacunas historial);
    HistorialDeVacunas buscarPorMascota(Mascota mascota);
    List<HistorialDeVacunas> listarPorUsuario(Usuario usuario);
    HistorialDeVacunas buscarPorId(Long id);
}
