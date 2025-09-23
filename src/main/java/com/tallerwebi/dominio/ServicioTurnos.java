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

    public List<String> getHorariosDisponibles(String fecha) {
        //Hardcodeado:
        List<String> horarios = new ArrayList<>();
        horarios.add("11:00");
        horarios.add("14:00");
        horarios.add("15:00");
        horarios.add("17:00");
        return horarios; 
    }
}
