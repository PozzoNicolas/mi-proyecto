package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Veterinaria {

    private String nombre; 
    private Integer id; 
    private Map<String, List<Profesional>> profesionalesEnHorario = new HashMap<>(); ;

    public Veterinaria() {}

    public Veterinaria(Integer id, String nombre) {
        this.nombre = nombre; 
        this.id = id; 
        this.profesionalesEnHorario = new HashMap<>(); 
    }

    public Integer getId() {
        return this.id; 
    }

    public String getNombre() {
        return this.nombre; 
    }

    public List<Profesional> getProfesionalesEnHorario(String horario) {
        return this.profesionalesEnHorario.getOrDefault(horario, new ArrayList<>()); 
    }

    public Map<String, List<Profesional>> getProfesionalesEnHorario() {
        return profesionalesEnHorario;
    }

    public void agregarProfesionalEnHorario(String horario, Profesional profesional) {
        profesionalesEnHorario.computeIfAbsent(horario, h -> new ArrayList<>()).add(profesional); 
    }
}
