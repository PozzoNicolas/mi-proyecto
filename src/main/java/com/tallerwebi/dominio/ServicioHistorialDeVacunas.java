package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioHistorialDeVacunas {

    //Get all historiales de un usuario
    //Get historial de una mascota en particular
    //Crear un historial y asosiar a una mascota
    //Modificar/Editar historial

    //Calcular vacunas restantes ??? Funcionalidad a definir
    void crearUnHistorialDeVacunas(HistorialDeVacunasDTO HistorialDeVacunasDTO);
    HistorialDeVacunas getHistorialDeUnaMascota(Mascota mascota);
    List<HistorialDeVacunas> listarTodosLosHistorialesDeUnUsuario(Usuario usuario);
    HistorialDeVacunas getPorId(Long id);
}
