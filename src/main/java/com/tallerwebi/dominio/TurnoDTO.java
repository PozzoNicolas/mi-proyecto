package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class TurnoDTO {
    private Integer id;
    private static int contadorIds = 1;
    private Especialidad especialidad;
    private Practica practica;
    private Integer veterinariaId;
    private String fecha;
    private String horario;
    private Long profesionalId;

    // Campo temporal para form en resultado-turnos.
    private String seleccion;

    public TurnoDTO() {
        this.id = contadorIds++; // A cambiar
    }

    public TurnoDTO(Especialidad especialidad, Practica practica, Integer veterinariaId, String fecha, String horario) {
        this.id = contadorIds++; // A cambiar
        this.especialidad = especialidad;
        this.practica = practica;
        this.veterinariaId = veterinariaId;
        this.fecha = fecha;
        this.horario = horario;
    }

    public Integer getId() {
        return this.id;
    }

    public Especialidad getEspecialidad() {
        return this.especialidad;
    }

    public Practica getPractica() {
        return this.practica;
    }

    public Integer getVeterinariaId() {
        return this.veterinariaId;
    }

    public String getFecha() {
        return this.fecha;
    }

    public String getHorario() {
        return this.horario;
    }

    public Long getProfesionalId() {
        return this.profesionalId;
    }

    public String getSeleccion() {
        return this.seleccion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setPractica(Practica practica) {
        this.practica = practica;
    }

    public void setVeterinariaId(Integer veterinariaId) {
        this.veterinariaId = veterinariaId;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public void setSeleccion(String n) {
        this.seleccion = n;
    }

    public LocalDate getFechaComoLocalDate() {
        return LocalDate.parse(this.fecha);
    }

    public LocalTime getHorarioComoLocalTime() {
        return LocalTime.parse(this.horario);
    }
}
