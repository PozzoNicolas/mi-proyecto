package com.tallerwebi.dominio;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.tallerwebi.dominio.enums.Vacuna;

public class VacunacionDTO {
    private Long historialId;
    private Vacuna vacuna;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    
    public Long getHistorialId() {
        return historialId;
    }
    public void setHistorialId(Long historialId) {
        this.historialId = historialId;
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

    
}