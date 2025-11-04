package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioRecomendacion {
    void guardarRecomendacion (Recomendacion recomendacion);
    List <Recomendacion> listarPorUsuario (Usuario usuario);


}