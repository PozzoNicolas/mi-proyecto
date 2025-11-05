package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioProfesionalImpl implements ServicioProfesional {
    
    private RepositorioProfesional repositorioProfesional;

    @Autowired
    public ServicioProfesionalImpl(RepositorioProfesional repositorioProfesional) {
//        AGREGAR A data.sql
//        Profesional p1 = new Profesional("Ernesto Sabato", 111);
//        Profesional p2 = new Profesional("H. G. Osterheld", 222);
//        Profesional p3 = new Profesional("Mariana Enriquez", 333);
        this.repositorioProfesional = repositorioProfesional;
    }

    @Override
    public Profesional buscarPorDni(Integer dni) {
        return repositorioProfesional.buscarPorDni(dni);
    }
}
