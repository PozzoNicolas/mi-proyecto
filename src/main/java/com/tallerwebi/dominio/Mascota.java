package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.*;

@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String nombre;
    private String tipoDeMascota;
    private String raza;
    private Integer edad;
    private LocalDate fechaDeNacimiento; 
    private String sexo;
    @ManyToOne
    @JoinColumn (name = "id_usuario")
    private Usuario duenio;


    //Constructores: siempre que definamos un constructor con parámetros, tenemos que crear un constructor vacío.
    public Mascota() { }

    //Queda por los tests...
    public Mascota (String nombre, String tipoDeMascota, String raza, Integer edad, String sexo) {
        this.nombre = nombre;
        this.tipoDeMascota = tipoDeMascota;
        this.raza = raza;
        this.edad = edad;
        this.sexo = sexo;
    }

    public Mascota (String nombre, String tipoDeMascota, String raza, Integer edad, String sexo, LocalDate fecha) {
        this.nombre = nombre;
        this.tipoDeMascota = tipoDeMascota;
        this.raza = raza;
        this.fechaDeNacimiento = fecha;
        this.edad = this.getEdad();
        this.sexo = sexo;
    }


    //Metodos
    public Long getId() {
        return id;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeMascota() {
        return tipoDeMascota;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        if (fechaDeNacimiento == null) return 0;
        return Period.between(fechaDeNacimiento, LocalDate.now()).getYears();
    }

    public Usuario getDuenio() {
        return duenio;
    }

    public LocalDate getFechaDeNacimiento(){
        return this.fechaDeNacimiento; 
    }

    public void setDuenio(Usuario duenio) {
        this.duenio = duenio;
    }

    public void setFechaDeNacimiento(LocalDate fecha) {
        this.fechaDeNacimiento = fecha;
    }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public void setTipoDeMascota(String tipoDeMascota) {
    }
}
