package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class TurnoVistaDTO {

    private Long id;
    private String especialidad;
    private String practica;
    private LocalDate fecha;
    private LocalTime horario;
    private String veterinariaNombre;
    private String profesionalNombre;

    // Constructor
    public TurnoVistaDTO(Long id, String especialidad, String practica,
                         LocalDate fecha, LocalTime horario,
                         String veterinariaNombre, String profesionalNombre) {
        this.id = id;
        this.especialidad = especialidad;
        this.practica = practica;
        this.fecha = fecha;
        this.horario = horario;
        this.veterinariaNombre = veterinariaNombre;
        this.profesionalNombre = profesionalNombre;
    }

    public TurnoVistaDTO() {
        //TODO Auto-generated constructor stub
    }

    public Long getId() {
        return this.id;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public String getPractica() {
        return this.practica;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public LocalTime getHorario() {
        return this.horario;
    }

    public String getVeterinariaNombre() {
        return this.veterinariaNombre;
    }

    public String getProfesionalNombre() {
        return this.profesionalNombre; 
    }

    public void setId(Long id) {this.id = id;}
    public void setEspecialidad(String especialidad) {this.especialidad = especialidad;}
    public void setPractica(String practica) {this.practica = practica;}
    public void setVeterinariaNombre(String veterinariaNombre) {this.veterinariaNombre = veterinariaNombre;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public void setHorario(LocalTime horario) {this.horario = horario;}
    public void setProfesionalNombre(String profesionalNombre) {this.profesionalNombre = profesionalNombre;}
}
