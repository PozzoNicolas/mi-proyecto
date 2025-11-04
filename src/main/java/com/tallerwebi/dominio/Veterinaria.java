package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Veterinaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

    @Transient
    private Map<String, List<Profesional>> profesionalesEnHorario = new HashMap<>(); ;

    public Veterinaria() {}

    public Veterinaria(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.profesionalesEnHorario = new HashMap<>(); 
    }

    public Long getId() {
        return this.id; 
    }

    public String getNombre() {
        return this.nombre; 
    }

    public List<Profesional> getProfesionalesEnHorario(String horario) {
        return this.profesionalesEnHorario.getOrDefault(horario, new ArrayList<>()); 
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Map<String, List<Profesional>> getProfesionalesEnHorario() {
        return profesionalesEnHorario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfesionalesEnHorario(Map<String, List<Profesional>> profesionalesEnHorario) {
        this.profesionalesEnHorario = profesionalesEnHorario; 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion; 
    }

    public void agregarProfesionalEnHorario(String horario, Profesional profesional) {
        profesionalesEnHorario.computeIfAbsent(horario, h -> new ArrayList<>()).add(profesional); 
    }

    @Override
    public String toString() {
        return "Veterinaria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", profesionalesEnHorario=" + profesionalesEnHorario +
                '}';
    }
}
