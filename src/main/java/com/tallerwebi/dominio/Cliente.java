package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    /*Uso mappedBy para que no se cree una tabla intermedia y Mascota lleva una FK en su atributo 'duenio'.
    Es una relación de 1:N donde un cliente puede tener muchas mascotas.*/
    @OneToMany(mappedBy = "duenio")
    private List<Mascota> mascotas = new ArrayList<Mascota>();
    @Transient
    private List<Turno> turnos = new ArrayList<Turno>();

    public Cliente () {
    }

    public Cliente (Long id, String nombre, String apellido, String correo, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public boolean cancelarTurno(Integer idTurno) {
        if(turnos.removeIf(turno -> turno.getId() == idTurno)) {
            return true;
        } else throw new IllegalArgumentException("Turno no encontrado o no eliminado");
    }

    public Turno getTurnoPorId(Integer turnoId) {
        Turno turno = this.getTurnos().stream()
                .filter(t -> t.getId().equals(turnoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));
        return turno; 
    }
}
