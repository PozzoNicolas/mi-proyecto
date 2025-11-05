package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioTurnos {

    void guardar(Turno turno);
    List<Turno> obtenerTurnosPorVeterinariaYFecha(Long vetId, LocalDate fecha);
    List<Turno> obtenerTurnosProximos();
}
