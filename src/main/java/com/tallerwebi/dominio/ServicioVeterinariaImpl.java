package com.tallerwebi.dominio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioVeterinariaImpl implements ServicioVeterinaria {

    private final RepositorioVeterinaria repositorioVeterinaria;
    private final RepositorioProfesional repositorioProfesional;

    @Autowired
    public ServicioVeterinariaImpl(RepositorioVeterinaria repositorioVeterinaria,
                                   RepositorioProfesional repositorioProfesional) {
        this.repositorioVeterinaria = repositorioVeterinaria;
        this.repositorioProfesional = repositorioProfesional;
    }

    @Override
    public List<Veterinaria> listarVeterinarias() {
        return repositorioVeterinaria.listarVeterinarias();
    }

    @Override
    public Veterinaria buscarPorId(Long id) {
        Veterinaria vet = repositorioVeterinaria.buscarPorId(id);
        return vet != null ? vet : new Veterinaria();
    }

}
