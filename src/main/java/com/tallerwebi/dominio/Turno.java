package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private static int contadorIds = 1; 
    private Especialidad especialidad;
    private Practica practica;
    private Integer veterinaria; 
    private String fecha;
    private String horario;
    private String profesional;
    @ManyToOne
    @JoinColumn (name = "id_usuario")
    private Usuario usuario;

    //Campo temporal para form en resultado-turnos.
    private String seleccion;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Turno() {
        this.id = contadorIds++; //A cambiar
    }

    public Turno(Especialidad especialidad, Practica practica, Integer veterinaria, String fecha, String horario ) {
        this.id = contadorIds++; //A cambiar
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
    public String getSeleccion() {return this.seleccion;}

    public void setId(int id) {this.id = id;}
    public void setEspecialidad(Especialidad especialidad) {this.especialidad = especialidad;}
    public void setPractica(Practica practica) {this.practica = practica;}
    public void setVeterinaria(Integer veterinariaId) {this.veterinaria = veterinariaId;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public void setHorario(String horario) {this.horario = horario;}
    public void setProfesional(String profesional) {this.profesional = profesional;}
    public void setSeleccion(String n) {this.seleccion = n;}
}
