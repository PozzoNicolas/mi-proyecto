package com.tallerwebi.dominio;

public class Mascota {

    private String nombre;
    private String tipoDeMascota;
    private String raza;
    private Integer edad;
    private Cliente duenio;

    public Mascota() { }
    public Mascota (String nombre, String tipoDeMascota, String raza, Integer edad) {
        this.nombre = nombre;
        this.tipoDeMascota = tipoDeMascota;
        this.raza = raza;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDeMascota() {
        return tipoDeMascota;
    }

    public String getRaza() {
        return raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public Cliente getDuenio() {
        return duenio;
    }

    public void setDuenio(Cliente duenio) {
        this.duenio = duenio;
    }
}
