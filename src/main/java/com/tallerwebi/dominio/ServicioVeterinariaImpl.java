package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioVeterinariaImpl implements ServicioVeterinaria {

    private final Map<Long, Veterinaria> storage = new LinkedHashMap<>();

    @Autowired
    public ServicioVeterinariaImpl(ServicioProfesional servicioProfesional) {
        Veterinaria v1 = new Veterinaria("VetUno","Rivadavia 9128");
        v1.setId(1L);
        Veterinaria v2 = new Veterinaria("Vete Dos", "Juan b. Justo");
        v2.setId(2L);

        //Profesionales En horarios hardcodeados para podes sacar turos: 
        v1.agregarProfesionalEnHorario("10:00", servicioProfesional.buscarPorDni(111));
        v1.agregarProfesionalEnHorario("10:00", servicioProfesional.buscarPorDni(222));
        v1.agregarProfesionalEnHorario("11:00", servicioProfesional.buscarPorDni(111));

        v2.agregarProfesionalEnHorario("14:00", servicioProfesional.buscarPorDni(333));
        v2.agregarProfesionalEnHorario("15:00", servicioProfesional.buscarPorDni(333));

        storage.put(v1.getId(), v1);
        storage.put(v2.getId(), v2); 
    }

    @Override
    public List<Veterinaria> listarVeterinarias() {
        return new ArrayList<>(storage.values()); 
    }

    @Override
    public Veterinaria buscarPorId(Long id) {
        Veterinaria v = storage.get(id);
        if (v == null) {
            v = new Veterinaria();
        }
        return v; 
    }

}
