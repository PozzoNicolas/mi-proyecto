package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Vacuna;

public interface ServicioHistorialDeVacunas {
    void crearUnHistorialDeVacunas(HistorialDeVacunasDTO HistorialDeVacunasDTO);
    HistorialDeVacunas getHistorialDeUnaMascota(Mascota mascota);
    List<HistorialDeVacunas> listarTodosLosHistorialesDeUnUsuario(Usuario usuario);
    HistorialDeVacunas getPorId(Long id);
    void guardar(HistorialDeVacunas historial);

    List<Vacuna> getVacunasSinAplicar(Long id_mascota); 
}
