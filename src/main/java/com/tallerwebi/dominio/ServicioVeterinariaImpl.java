package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class ServicioVeterinariaImpl implements ServicioVeterinaria {

    private final Map<Integer, Veterinaria> storage = new LinkedHashMap<>();  
    //private final AtomicInteger idGenerator = new AtomicInteger(1);

    public ServicioVeterinariaImpl() {
        Veterinaria v1 = new Veterinaria(001,"VetUno");
        Veterinaria v2 = new Veterinaria(002, "Vete Dos"); 
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
