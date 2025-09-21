package com.tallerwebi.dominio;

public class Cliente {

    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

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

    

    

}
