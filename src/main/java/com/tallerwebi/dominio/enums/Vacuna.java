package com.tallerwebi.dominio.enums;

public enum Vacuna {
    MOQUILLO("Moquillo", "Perro", true, 6, 8),
    PARVOVIRUS("Parvovirus", "Perro", true, 6, 8),
    HEPATITIS("Hepatitis infecciosa canina", "Perro", true, 8, 10),
    LEPTOSPIROSIS("Leptospirosis", "Perro", true, 10, 12),
    RABIA_PERRO("Rabia", "Perro", true, 12, 16),
    TOS_DE_LAS_PERRERAS("Tos de las perreras (Bordetella)", "Perro", true, 12, 16),

    TRIPLE_FELINA("Triple felina (Panleucopenia, Rinotraqueítis, Calicivirus)", "Gato", true, 8, 10),
    LEUCEMIA_FELINA("Leucemia felina (FeLV)", "Gato", true, 8, 12),
    RABIA_GATO("Rabia", "Gato", true, 12, 16);

    private final String desc;
    private final String tipoDeMascota;
    private final boolean refuerzoAnual;
    private final int minSemanas;
    private final int maxSemanas;

    Vacuna(String desc, String tipoDeMascota, boolean refuerzoAnual, int minSemanas, int maxSemanas) {
        this.desc = desc;
        this.tipoDeMascota = tipoDeMascota;
        this.refuerzoAnual = refuerzoAnual;
        this.minSemanas = minSemanas;
        this.maxSemanas = maxSemanas;
    }

    public String getDesc() {
        return desc;
    }

    public String getTipoDeMascota() {
        return tipoDeMascota;
    }

    public boolean isRefuerzoAnual() {
        return refuerzoAnual;
    }

    public int getMinSemanas() {
        return minSemanas;
    }

    public int getMaxSemanas() {
        return maxSemanas;
    }
}
