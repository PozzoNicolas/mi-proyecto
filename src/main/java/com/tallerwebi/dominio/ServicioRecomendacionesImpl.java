package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioRecomendacionesImpl implements ServicioRecomendaciones {


    @Override
    public List<String> generarRecomendaciones(Usuario usuario) {
        List<String> recomendaciones = new ArrayList<>();
        if (usuario == null || usuario.getMascotas() == null || usuario.getMascotas().isEmpty()) {
            recomendaciones.add("Aún no registraste mascotas. ¡Hacelo para recibir consejos personalizados!");
            return recomendaciones;
        }

        usuario.getMascotas().forEach(mascota -> {
            int edad = mascota.getEdad() != null ? mascota.getEdad() : 0;
            String tipo = mascota.getTipoDeMascota() != null ? mascota.getTipoDeMascota().toLowerCase() : "mascota";
            String nombre = mascota.getNombre();
            String sexo = mascota.getSexo() != null ? mascota.getSexo().toLowerCase() : "indefinido";


            if (tipo.equals("perro")) {
                if (edad <= 1) {
                    recomendaciones.add(nombre + " (cachorro): Asegurate de aplicar todas las vacunas y comenzar con la socialización temprana.");
                } else if (edad <= 7) {
                    recomendaciones.add(nombre + ": Mantené una rutina de paseos y una alimentación balanceada.");
                } else {
                    recomendaciones.add(nombre + ": Controlá su peso y hacé chequeos más frecuentes por posibles problemas articulares.");
                }


                if (sexo.equals("macho")) {
                    recomendaciones.add(nombre + ": Evitá conductas territoriales con ejercicios y castración si el veterinario lo recomienda.");
                } else if (sexo.equals("hembra")) {
                    recomendaciones.add(nombre + ": Controlá los períodos de celo y considerá la esterilización para evitar tumores mamarios.");
                }


            } else if (tipo.equals("gato")) {
                if (edad <= 1) {
                    recomendaciones.add(nombre + " (gatito): Brindale juguetes para estimular su curiosidad y completá su calendario de vacunas.");
                } else if (edad <= 7) {
                    recomendaciones.add(nombre + ": Asegurate de que tenga un rascador, buena hidratación y visitas veterinarias anuales.");
                } else {
                    recomendaciones.add(nombre + ": Prestá atención a su movilidad y controlá la función renal en chequeos periódicos.");
                }

                // Diferencias por sexo
                if (sexo.equals("macho")) {
                    recomendaciones.add(nombre + ": Mantené su entorno tranquilo para evitar el marcaje con orina.");
                } else if (sexo.equals("hembra")) {
                    recomendaciones.add(nombre + ": Si no está esterilizada, observá su comportamiento durante el celo y consultá al veterinario.");
                }


            } else {
                if (edad <= 1) {
                    recomendaciones.add(tipo + " " + nombre + ": Asegurate de aplicar todas las vacunas y realizar revisiones tempranas.");
                } else if (edad <= 7) {
                    recomendaciones.add(tipo + " " + nombre + ": Mantené su actividad física y controlá su alimentación.");
                } else {
                    recomendaciones.add(tipo + " " + nombre + ": Realizá chequeos más frecuentes y vigilá signos de envejecimiento.");
                }
            }
        });
        return recomendaciones;

    }


}

