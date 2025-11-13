package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Vacuna;

public interface RepositorioHistorialDeVacunas {
    void guardar(HistorialDeVacunas historial);
    HistorialDeVacunas buscarPorMascota(Mascota mascota);
    List<HistorialDeVacunas> listarPorUsuario(Usuario usuario);
    HistorialDeVacunas buscarPorId(Long id);
}
