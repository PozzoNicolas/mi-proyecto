package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    /*Uso mappedBy para que no se cree una tabla intermedia y Mascota lleva una FK en su atributo 'duenio'.
    Es una relación de 1:N donde un cliente puede tener muchas mascotas.*/
    @OneToMany(mappedBy = "duenio")
    private List<Mascota> mascotas = new ArrayList<Mascota>();
    @Transient
    private List<Turno> turnos = new ArrayList<Turno>();

    public Usuario(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public String getNombre() {return nombre;}
    public String getTelefono() {return telefono;}
    public void setTelefono(String telefonoUsuario) {this.telefono = telefonoUsuario;}
    public void setNombre(String nombreUsuario) {
        this.nombre = nombreUsuario;
    }
    public String getApellido(){return apellido;}
    public void setApellido(String apellidoUsuario) {
        this.apellido = apellidoUsuario;
    }
    public List<Mascota> getMascotas() {
        return mascotas;
    }
    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
    public List<Turno> getTurnos() {
        return turnos;
    }
    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
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
