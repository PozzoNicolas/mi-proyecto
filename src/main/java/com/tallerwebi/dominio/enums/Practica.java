package com.tallerwebi.dominio.enums;

public enum Practica {
    VACUNA_1("Moquillo"),
    VACUNA_2("Parvovirus"),
    VACUNA_3("Hepatitis infecciosa canina"),
    VACUNA_4("Leptospirosis"),
    VACUNA_5("Rabia"),
    VACUNA_6("Tos de las perreras (Bordetella)"),
    VACUNA_7("Triple felina (Panleucopenia, Rinotraqueítis, Calicivirus)"),
    VACUNA_8("Leucemia felina (FeLV)"),

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
