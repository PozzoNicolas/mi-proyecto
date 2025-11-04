package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "veterinaria_profesional_horario")
public class VeterinariaProfesionalHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinaria_id", nullable = false)
    private Veterinaria veterinaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id", nullable = false)
    private Profesional profesional;

    @Column
    private LocalTime horario;

    // constructores, getters y setters
    public VeterinariaProfesionalHorario() {}
    public VeterinariaProfesionalHorario(Veterinaria v, Profesional p, LocalTime horario) {
        this.veterinaria = v;
        this.profesional = p;
        this.horario = horario;
    }

    public Long getId() { return id; }
    public Veterinaria getVeterinaria() { return veterinaria; }
    public Profesional getProfesional() { return profesional; }
    public LocalTime getHorario() { return horario; }

    public void setId(Long id) { this.id = id; }
    public void setVeterinaria(Veterinaria veterinaria) { this.veterinaria = veterinaria; }
    public void setProfesional(Profesional profesional) { this.profesional = profesional; }
    public void setHorario(LocalTime horario) { this.horario = horario; }
}
