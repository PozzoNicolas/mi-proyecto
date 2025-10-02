package com.tallerwebi.dominio;

public interface ServicioTurnos {
    Turno registrarTurno();
    Turno buscarTurnoPorId(int id);
}
