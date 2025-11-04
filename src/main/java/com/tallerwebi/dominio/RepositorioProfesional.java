package com.tallerwebi.dominio;

public interface RepositorioProfesional {

    Profesional buscarPorId(Long id);
    Profesional buscarPorDni(Integer dni);
}
