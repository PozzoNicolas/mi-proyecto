package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ServicioTurnos {
    private final List<Turno> turnos = new ArrayList<>();

    public List<Turno> getTurnos() {
        return this.turnos;
    }

    public void registrarTurno(Turno turno) {
        this.turnos.add(turno);
    }
}
