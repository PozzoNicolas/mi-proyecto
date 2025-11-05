package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioVeterinaria {

    Veterinaria buscarPorId(Long id);
    List<Veterinaria> listarVeterinarias();
    void guardar(Veterinaria veterinaria);
}
