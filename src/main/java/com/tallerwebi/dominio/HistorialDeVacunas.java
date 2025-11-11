package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historial_vacunas")
public class HistorialDeVacunas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1 a 1 con Mascota
    @OneToOne
    @JoinColumn(name = "id_mascota", nullable = false, unique = true)
    private Mascota mascota;

    // Relación 1 a muchos con Vacunacion
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_historial") 
    private List<Vacunacion> vacunaciones = new ArrayList<>();
    
    public HistorialDeVacunas() {
    }

    public HistorialDeVacunas(Mascota mascota) {
        this.mascota = mascota;
    }

    public void agregarVacunacion(Vacunacion vacunacion) {
        vacunaciones.add(vacunacion);
    }

    public void eliminarVacunacion(Vacunacion vacunacion) {
        vacunaciones.remove(vacunacion);
    }

    public Long getId() {
        return id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public List<Vacunacion> getVacunaciones() {
        return vacunaciones;
    }

    public void setVacunaciones(List<Vacunacion> vacunaciones) {
        this.vacunaciones = vacunaciones;
    }

    @Override
    public String toString() {
        return "HistorialDeVacunas{" +
                "id=" + id +
                ", mascota=" + (mascota != null ? mascota.getNombre() : "null") +
                ", vacunaciones=" + vacunaciones +
                '}';
    }
}
