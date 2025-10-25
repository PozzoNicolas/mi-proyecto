package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Long telefonoUsuario;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;

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
    public String getNombreUsuario() {return nombreUsuario;}
    public Long getTelefonoUsuario() {return telefonoUsuario;}
    public void setTelefonoUsuario(Long telefonoUsuario) {this.telefonoUsuario = telefonoUsuario;}


    public void setNombre(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getApellidoUsuario  (){return apellidoUsuario;}
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }
}
