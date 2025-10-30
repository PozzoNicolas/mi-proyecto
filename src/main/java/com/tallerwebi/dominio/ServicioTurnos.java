package com.tallerwebi.dominio;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ServicioTurnos {
    boolean esTurnoValido(Turno turno);
    Veterinaria obtenerVeterinariaPorTurno(Turno turno); 
    List<Veterinaria> listarVeterinariasIndiferente(); 
    void procesarSeleccion(Turno turno); 
    void guardarTurno(Usuario usuario, Turno turno);
    void envioReportesTurnosProximos();
}
