package com.tallerwebi.dominio;

public class Turno {
    //Atributos a cambiar
    private int id;
    private String especialidad;
    private String practica;
    private String lugar; 
    private String fecha;
    private String horario;

    public Turno() {}

    public Turno(int id, String descripcion, String practica, String lugar, String fecha, String horario) {
        this.id = id;
        this.especialidad = descripcion;
        this.practica = practica;
        this.lugar = lugar; 
        this.fecha = fecha;
        this.horario = horario; 
    }

    public int getId() {return this.id;}
    public String getEspecialidad() {return this.especialidad;}
    public String getPractica() {return this.practica;}
    public String getLugar() {return this.lugar;}
    public String getFecha() {return this.fecha;}
    public String getHorario() {return this.horario;}

    public void setId(int n) {this.id = n;}
    public void setEspecialidad(String n) {this.especialidad = n;}
    public void setPractica(String n) {this.practica = n;}
    public void setLugar(String n) {this.lugar = n;}
    public void setFecha(String n) {this.fecha = n;}
    public void setHorario(String n) {this.horario = n;}
}
