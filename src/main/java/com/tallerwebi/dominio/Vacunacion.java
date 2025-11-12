package com.tallerwebi.dominio;

import javax.persistence.*;

import com.tallerwebi.dominio.enums.Vacuna;

import java.time.LocalDate;

@Entity
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Vacuna vacuna;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_historial")
    private HistorialDeVacunas historial;

    public HistorialDeVacunas getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialDeVacunas historial) {
        this.historial = historial;
    }

    public Vacunacion() {
    }

    public Vacunacion(Vacuna vacuna, LocalDate fecha) {
        this.vacuna = vacuna;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Vacunacion{" +
                "id=" + id +
                ", vacuna=" + vacuna +
                ", fecha=" + fecha +
                '}';
    }
}
