package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private List<Mascota> mascotas = new ArrayList<Mascota>();
    private List<Turno> turnos = new ArrayList<Turno>(); 

    public Cliente () {
    }

    public Cliente (Integer id, String nombre, String apellido, String correo, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer nextId) {
        this.id = nextId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo
                + ", telefono=" + telefono + "]";
    }


    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public List<Turno> getTurnos() {
        return this.turnos;
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

}
