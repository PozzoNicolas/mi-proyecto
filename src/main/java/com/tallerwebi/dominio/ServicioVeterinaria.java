package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioVeterinaria {
    List<Veterinaria> listarVeterinarias(); 
    Veterinaria buscarPorId(Long id);
}
