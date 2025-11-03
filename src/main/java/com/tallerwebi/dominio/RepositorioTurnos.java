package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTurnos {
    void guardar(Turno turno);

    List<Turno> obtenerTurnosProximos();
}
