package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true) // Solo lee, no modifica datos
public class ServicioEnlaceAdopcionImpl implements ServicioEnlaceAdopcion {

    private final RepositorioEnlaceAdopcion repositorioEnlaceAdopcion;

    @Autowired
    public ServicioEnlaceAdopcionImpl(RepositorioEnlaceAdopcion repositorioEnlaceAdopcion) {
        this.repositorioEnlaceAdopcion = repositorioEnlaceAdopcion;
    }

    @Override
    public List<EnlaceAdopcion> obtenerTodosLosEnlaces() {
        // Delega la llamada a la capa de infraestructura (Repositorio)
        return repositorioEnlaceAdopcion.listarTodos();
    }
}