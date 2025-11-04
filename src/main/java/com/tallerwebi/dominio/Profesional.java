package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre; 
    private Integer dni; 

    public Profesional() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }
}
