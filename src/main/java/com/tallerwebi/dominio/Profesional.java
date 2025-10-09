package com.tallerwebi.dominio;

public class Profesional {
    private String nombre; 
    private Integer dni; 

    public Profesional(String nombre, Integer dni) {
        this.nombre = nombre; 
        this.dni = dni; 
    }

    public String getNombre() {
        return this.nombre; 
    }

    public Integer getDni() {
        return this.dni; 
    }
}
