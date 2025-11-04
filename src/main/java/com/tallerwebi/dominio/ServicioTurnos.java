package com.tallerwebi.dominio;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.List;

public interface ServicioTurnos {
    boolean esTurnoValido(Turno turno);
    Veterinaria obtenerVeterinariaPorTurno(Turno turno); 
    List<Veterinaria> listarVeterinariasIndiferente(); 
    void procesarSeleccion(Turno turno); 
    void guardarTurno(Usuario usuario, Turno turno);
    void envioReportesTurnosProximos();
    public List<String> horariosDisponibles(Long idVet);
    public List<Profesional> profesionalesPorVeterinariaYHorario(Long idVet, LocalTime horario);
    public List<Profesional> profesionalesDisponibles(Long idVet, LocalTime horario);
}
