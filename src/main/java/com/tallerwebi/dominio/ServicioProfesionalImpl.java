package com.tallerwebi.dominio;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ServicioProfesionalImpl implements ServicioProfesional {
    private final Map<Integer, Profesional> storage = new LinkedHashMap<>();  

    public ServicioProfesionalImpl() {
        Profesional p1 = new Profesional("Ernesto Sabato", 111);
        Profesional p2 = new Profesional("H. G. Osterheld", 222);
        Profesional p3 = new Profesional("Mariana Enriquez", 333);
        storage.put(p1.getDni(), p1); 
        storage.put(p2.getDni(), p2);
        storage.put(p3.getDni(), p3);
    }

    @Override
    public Profesional buscarPorDni(Integer dni) {
        return storage.get(dni); 
    }
}
