package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

import javax.persistence.*;
import javax.transaction.Transactional;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Transactional
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Enumerated(EnumType.STRING)
    private Practica practica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinaria_id")
    private Veterinaria veterinaria;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private LocalTime horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Turno() {
    }

    public Turno(Especialidad especialidad, Practica practica, Veterinaria veterinaria,
                 Profesional profesional, LocalDate fecha, LocalTime horario ) {
        this.especialidad = especialidad;
        this.practica = practica;
        this.veterinaria = veterinaria; 
        this.fecha = fecha;
        this.horario = horario;
        this.profesional = profesional;
    }

    public Long getId() {return this.id;}
    public Especialidad getEspecialidad() {return this.especialidad;}
    public Practica getPractica() {return this.practica;}
    public Veterinaria getVeterinaria() {return this.veterinaria;}
    public LocalDate getFecha() {return this.fecha;}
    public LocalTime getHorario() {return this.horario;}
    public Profesional getProfesional() {return this.profesional;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setEspecialidad(Especialidad especialidad) {this.especialidad = especialidad;}
    public void setPractica(Practica practica) {this.practica = practica;}
    public void setVeterinaria(Veterinaria veterinariaId) {this.veterinaria = veterinariaId;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public void setHorario(LocalTime horario) {this.horario = horario;}
    public void setProfesional(Profesional profesional) {this.profesional = profesional;}
}
