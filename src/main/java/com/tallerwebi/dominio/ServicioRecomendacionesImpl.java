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

            return recomendacionesFinales;
        }

        usuario.getMascotas().forEach(mascota -> {
            // 1. Determinar los criterios de búsqueda (Tipo, Etapa, Sexo)
            String tipo = determinarTipo(mascota);
            String etapa = determinarEtapa(mascota);
            String sexo = determinarSexo(mascota);

            // 2. Llamar al repositorio para obtener las recomendaciones de la DB
            List<Recomendacion> recomendacionesPorMascota =
                    repositorioRecomendacion.buscarPorCriterios(tipo, etapa, sexo);

            // 3. Agregar los resultados a la lista final
            recomendacionesFinales.addAll(recomendacionesPorMascota);
        });

        // Opcional: Eliminar duplicados si una misma recomendación aplica a varias mascotas
        // Aunque por ahora no es necesario, ya que estamos solo agregando.

        return recomendacionesFinales;
    }

    // --- Métodos de apoyo para estandarizar los filtros ---

    private String determinarTipo(Mascota mascota) {
        // Asumiendo que TipoDeMascota devuelve "Perro", "Gato", etc.
        return mascota.getTipoDeMascota() != null ? mascota.getTipoDeMascota() : "General";
    }

    private String determinarEtapa(Mascota mascota) {
        int edad = mascota.getEdad() != null ? mascota.getEdad() : 0;

        if (mascota.getTipoDeMascota().equalsIgnoreCase("Gato")) {
            // Criterios para Gatos (ejemplo)
            if (edad <= 1) return "Cachorro";    // 0 a 1 año (Kitten)
            if (edad <= 8) return "Adulto";      // 1 a 8 años
            return "Senior";                     // 8+ años
        } else {
            // Criterios para Perros (ejemplo)
            if (edad <= 1) return "Cachorro";    // 0 a 1 año (Puppy)
            if (edad <= 7) return "Adulto";      // 1 a 7 años
            return "Senior";                     // 7+ años
        }
    }

    private String determinarSexo(Mascota mascota) {
        // Retorna el sexo de la mascota, asumiendo "Macho", "Hembra", etc.
        return mascota.getSexo() != null ? mascota.getSexo() : "Ambos";
    }


}




