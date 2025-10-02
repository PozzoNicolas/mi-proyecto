package com.tallerwebi.dominio;

public class Veterinaria {

    private String nombre; 
    private Integer id; 

    public Veterinaria() {}
    public Veterinaria(Integer id, String nombre) {
        this.nombre = nombre; 
        this.id = id; 
    }

    public Integer getId() {
        return this.id; 
    }

    public String getNombre() {
        return this.nombre; 
    }
}
