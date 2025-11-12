package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service ("servicioRecomendaciones")
public class ServicioRecomendacionesImpl implements ServicioRecomendaciones {

    private final RepositorioRecomendacion repositorioRecomendacion;

    @Autowired
    public ServicioRecomendacionesImpl(RepositorioRecomendacion repositorioRecomendacion) {
        this.repositorioRecomendacion = repositorioRecomendacion;
    }


    @Override
    public List<Recomendacion> generarRecomendaciones(Usuario usuario) {
        List<Recomendacion> recomendacionesFinales = new ArrayList<>();
        if (usuario == null || usuario.getMascotas() == null || usuario.getMascotas().isEmpty()) {

            return new ArrayList<>();
        }

        Mascota mascota = usuario.getMascotas().get(0);

        String tipo = mascota.getTipoDeMascota() != null ? mascota.getTipoDeMascota() : "General";
        String sexo = mascota.getSexo() != null ? mascota.getSexo() : "Ambos";

        // Lógica de mapeo de edad a Etapa (Cachorro, Adulto, Senior)
        String etapa;
        int edad = mascota.getEdad();

        if (edad <= 1) {
            etapa = "Cachorro";
        } else if (edad <= 7) {
            etapa = "Adulto";
        } else {
            etapa = "Senior";
        }


        return repositorioRecomendacion.buscarPorCriterios(tipo, etapa, sexo);
    }

    // --- Métodos de apoyo para estandarizar los filtros ---

    private String determinarTipo(Mascota mascota) {

        String tipo = mascota.getTipoDeMascota();
        return tipo != null ?
                tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase() : // Capitalizar para HQL
                "General";
    }

    private String determinarEtapa(Mascota mascota) {
        int edad = mascota.getEdad();

        String tipo = mascota.getTipoDeMascota();

        if (tipo != null && tipo.equalsIgnoreCase("Gato")) {
            // Criterios para Gatos (ejemplo)
            if (edad <= 1) return "Cachorro";    // 0 a 1 año (Kitten)
            if (edad <= 8) return "Adulto";      // 1 a 8 años
            return "Senior";                     // 8+ años
        } else if (tipo != null && tipo.equalsIgnoreCase("Perro")) {
            // Criterios para Perros (ejemplo)
            if (edad <= 1) return "Cachorro";    // 0 a 1 año (Puppy)
            if (edad <= 7) return "Adulto";      // 1 a 7 años
            return "Senior";                     // 7+ años
        } else {
            return "General";
        }
    }

    private String determinarSexo(Mascota mascota) {
        // Retorna el sexo de la mascota, asumiendo "Macho", "Hembra", etc.
        return mascota.getSexo() != null ? mascota.getSexo() : "Ambos";
    }


}




