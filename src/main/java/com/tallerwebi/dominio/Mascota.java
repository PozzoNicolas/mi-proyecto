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
    @ManyToOne
    //Usamos JoinColumn para usar el atributo como FK y lo establecemos con el nombre 'id_cliente'
    @JoinColumn (name = "id_cliente")
    private Cliente duenio;


    //Constructores: siempre que definamos un constructor con parámetros, tenemos que crear un constructor vacío.
    public Mascota() { }

    public Mascota (String nombre, String tipoDeMascota, String raza, Integer edad) {
        this.nombre = nombre;
        this.tipoDeMascota = tipoDeMascota;
        this.raza = raza;
        this.edad = edad;
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

    public Cliente getDuenio() {
        return duenio;
    }

    public void setDuenio(Cliente duenio) {
        this.duenio = duenio;
    }


}
