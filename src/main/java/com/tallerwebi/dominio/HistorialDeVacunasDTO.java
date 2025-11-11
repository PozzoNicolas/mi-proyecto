package com.tallerwebi.dominio;

import java.util.List;

public class HistorialDeVacunasDTO {
    private Mascota mascota;
    private List<Vacunacion> vacunaciones;

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
}
