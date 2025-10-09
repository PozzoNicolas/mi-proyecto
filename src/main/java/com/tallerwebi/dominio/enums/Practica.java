package com.tallerwebi.dominio.enums;

/*
 * De momento dejo todo en un enum. Pero también podemos hacer
 * uno por cada especialidad en caso de agregar cositas a la hora
 * de sacar turnos, ej: Para tal estudio, recorda q tu mascota
 * tiene que estar en ayuno, etc etc...
 */
public enum Practica {
    VACUNA_1("Rabia"),
    VACUNA_2("Moquillo"),
    ESTUDIO_1("Radiografía"),
    ESTUDIO_2("Análisis de sangre"),
    CONTROL_1("Chequeo"); 

    private final String desc; 

    Practica(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc; 
    } 
}
