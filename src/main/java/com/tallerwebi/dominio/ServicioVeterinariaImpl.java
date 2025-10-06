package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioVeterinariaImpl implements ServicioVeterinaria {

    private final Map<Integer, Veterinaria> storage = new LinkedHashMap<>();  
    private final ServicioProfesional servicioProfesional; 

    @Autowired
    public ServicioVeterinariaImpl(ServicioProfesional servicioProfesional) {
        this.servicioProfesional = servicioProfesional;
        //Veterinarias Hardcodeadas
        Veterinaria v1 = new Veterinaria(1,"VetUno","Rivadavia 9128");
        Veterinaria v2 = new Veterinaria(2, "Vete Dos", "Juan b. Justo"); 

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
    public Veterinaria buscarPorId(Integer id) {
        return storage.get(id); 
    }

}
