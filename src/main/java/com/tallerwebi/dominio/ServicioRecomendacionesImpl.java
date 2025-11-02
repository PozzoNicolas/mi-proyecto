package com.tallerwebi.dominio;


import com.tallerwebi.dominio.RepositorioRecomendacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioRecomendacionesImpl implements ServicioRecomendaciones {

    private final RepositorioRecomendacion repositorioRecomendacion;

    @Autowired
    public ServicioRecomendacionesImpl(RepositorioRecomendacion repositorioRecomendacion) {
        this.repositorioRecomendacion = repositorioRecomendacion;
    }

    @Override
    public List<String> generarRecomendaciones(Usuario usuario) {
        List<String> recomendaciones = new ArrayList<>();

        if (usuario == null || usuario.getMascotas() == null || usuario.getMascotas().isEmpty()) {
            String mensaje = "Aún no registraste mascotas. ¡Hacelo para recibir consejos personalizados!";
            recomendaciones.add(mensaje);
            repositorioRecomendacion.guardarRecomendacion(new Recomendacion("Registra tu mascota", mensaje, "general", usuario));
            return recomendaciones;
        }

        usuario.getMascotas().forEach(mascota -> {
            int edad = mascota.getEdad() != null ? mascota.getEdad() : 0;
            String tipo = mascota.getTipoDeMascota() != null ? mascota.getTipoDeMascota() : "Mascota";
            String mensaje;

            if (edad <= 1) {
                mensaje = tipo + " " + mascota.getNombre() + ": Aplicá vacunas y hacé controles iniciales.";
            } else if (edad <= 7) {
                mensaje = tipo + " " + mascota.getNombre() + ": Mantené su actividad y buena alimentación.";
            } else {
                mensaje = tipo + " " + mascota.getNombre() + ": Realizá chequeos más frecuentes.";
            }

            recomendaciones.add(mensaje);
            repositorioRecomendacion.guardarRecomendacion(new Recomendacion("Consejo para " + mascota.getNombre(), mensaje, "salud", usuario));
        });

        return recomendaciones;
    }


}
