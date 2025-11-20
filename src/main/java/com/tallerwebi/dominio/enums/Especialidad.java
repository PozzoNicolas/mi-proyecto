package com.tallerwebi.dominio.enums;

import java.util.List; 

public enum Especialidad {
    VACUNA("Vacuna", List.of(Practica.VACUNA_1, Practica.VACUNA_2, Practica.VACUNA_3, Practica.VACUNA_4, Practica.VACUNA_5, Practica.VACUNA_6, Practica.VACUNA_7, Practica.VACUNA_8)),
    ESTUDIO("Estudio", List.of(Practica.ESTUDIO_1, Practica.ESTUDIO_2)),
    CONTROL("Control", List.of(Practica.CONTROL_1));

    private final String desc; 
    private final List<Practica> practicas; 

    Especialidad(String desc, List<Practica> practicas) {
        this.desc = desc;
        this.practicas = practicas; 
    }

    public String getDesc() {
        return this.desc; 
    }

    public List<Practica> getPracticas() {
        return this.practicas; 
    }
}
