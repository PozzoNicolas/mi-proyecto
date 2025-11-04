package com.tallerwebi.dominio;

import java.time.LocalTime;
import java.util.List;

public interface ServicioVPH {

    List<VeterinariaProfesionalHorario> obtenerProfesionalesDeVeterinaria(Long idVet);
    List<VeterinariaProfesionalHorario> obtenerProfesionalesEnHorario(Long idVet, LocalTime hora);
    void asignarProfesionalHorario(VeterinariaProfesionalHorario vph);
}
