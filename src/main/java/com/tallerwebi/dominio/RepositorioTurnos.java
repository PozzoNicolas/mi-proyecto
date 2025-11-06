package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RepositorioTurnos {

    void guardar(Turno turno);
    List<Turno> obtenerTurnosPorVeterinariaYFecha(Long vetId, LocalDate fecha);
    List<Turno> obtenerTurnosProximos();
    boolean existeTurno(Long vetId, Integer profesionalDni, LocalDate fecha, LocalTime horario);
}
