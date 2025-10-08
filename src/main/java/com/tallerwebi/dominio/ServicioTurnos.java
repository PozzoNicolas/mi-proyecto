package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioTurnos {
    boolean esTurnoValido(Turno turno);
    Veterinaria obtenerVeterinariaPorTurno(Turno turno); 
    List<Veterinaria> listarVeterinariasIndiferente(); 
    void procesarSeleccion(Turno turno); 
    void guardarTurno(Cliente cliente, Turno turno);
}
