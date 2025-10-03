package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

public class Turno {
    private Integer id;
    private Especialidad especialidad;
    private Practica practica;
    private Integer veterinaria; 
    private String fecha;
    private String horario;
    private String profesional;

    public Turno() {}

    public Turno(Integer id, Especialidad especialidad, Practica practica, Integer veterinaria, String fecha, String horario) {
        this.id = id;
        this.especialidad = especialidad;
        this.practica = practica;
        this.veterinaria = veterinaria; 
        this.fecha = fecha;
        this.horario = horario; 
    }

    public Integer getId() {return this.id;}
    public Especialidad getEspecialidad() {return this.especialidad;}
    public Practica getPractica() {return this.practica;}
    public Integer getVeterinaria() {return this.veterinaria;}
    public String getFecha() {return this.fecha;}
    public String getHorario() {return this.horario;}
    public String getProfesional() {return this.profesional;}

    public void setId(int n) {this.id = n;}
    public void setEspecialidad(Especialidad n) {this.especialidad = n;}
    public void setPractica(Practica n) {this.practica = n;}
    public void setVeterinaria(Integer n) {this.veterinaria = n;}
    public void setFecha(String n) {this.fecha = n;}
    public void setHorario(String n) {this.horario = n;}
    public void setProfesional(String n) {this.profesional = n;}
}
