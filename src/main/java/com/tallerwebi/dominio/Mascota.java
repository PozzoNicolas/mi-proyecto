package com.tallerwebi.dominio;

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
    private String sexo;
    @ManyToOne
    @JoinColumn (name = "id_usuario")
    private Usuario duenio;


    //Constructores: siempre que definamos un constructor con parámetros, tenemos que crear un constructor vacío.
    public Mascota() { }

    public Mascota (String nombre, String tipoDeMascota, String raza, Integer edad, String sexo) {
        this.nombre = nombre;
        this.tipoDeMascota = tipoDeMascota;
        this.raza = raza;
        this.edad = edad;
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

    public String getTipoDeMascota() {
        return tipoDeMascota;
    }

    public String getRaza() {
        return raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public Usuario getDuenio() {
        return duenio;
    }

    public void setDuenio(Usuario duenio) {
        this.duenio = duenio;
    }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public void setTipoDeMascota(String tipoDeMascota) {
    }
}
