package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ServicioTurnos {
    List<Veterinaria> listarVeterinariasIndiferente(); 
    void guardarTurno(Usuario usuario, Turno turno);
    void envioReportesTurnosProximos();
    public List<String> horariosDisponibles(Long idVet, LocalDate fecha);
    public List<Profesional> profesionalesPorVeterinariaYHorario(Long idVet, LocalTime horario);
    public List<Profesional> profesionalesDisponibles(Long idVet, LocalDate fecha, LocalTime horario);

    void procesarSeleccion(TurnoDTO turnoDTO); 
    //Veterinaria obtenerVeterinariaPorTurno(Turno turno); 
    Veterinaria getVeterinariaPorTurnoDTO(TurnoDTO turnoDTO);
    boolean esTurnoDTOEspPracValidas(TurnoDTO turnoDTO);
    Turno crearTurnoConDTO(TurnoDTO turnoDTO);
    TurnoVistaDTO mapearTurnoATurnoVistaDTO(Turno turno);
}
