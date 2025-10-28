package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioRecomendacionesImpl implements ServicioRecomendaciones {


    @Override
    public List<String> generarRecomendaciones(Cliente cliente) {
        List<String> recomendaciones = new ArrayList<>();
        if (cliente == null || cliente.getMascotas() == null || cliente.getMascotas().isEmpty()) {
            recomendaciones.add("Aún no registraste mascotas. ¡Hacelo para recibir consejos personalizados!");
            return recomendaciones;
        }

        cliente.getMascotas().forEach(mascota -> {
            int edad = mascota.getEdad() != null ? mascota.getEdad() : 0;
            String tipo = mascota.getTipoDeMascota() != null ? mascota.getTipoDeMascota() : "Mascota";

            if (edad <= 1) {
                recomendaciones.add("🐾 " + tipo + " " + mascota.getNombre() + ": Asegurate de aplicar todas las vacunas y realizar revisiones tempranas.");
            } else if (edad <= 7) {
                recomendaciones.add("🐕 " + tipo + " " + mascota.getNombre() + ": Mantené su actividad física y controlá su alimentación.");
            } else {
                recomendaciones.add("🐶 " + tipo + " " + mascota.getNombre() + ": Realizá chequeos más frecuentes y vigilá signos de envejecimiento.");
            }
        });
        return recomendaciones;
    }
}

