package com.tallerwebi.dominio;

import java.time.LocalTime;
import java.util.List;

public interface RepositorioVPH {
    List<VeterinariaProfesionalHorario> obtenerPorVeterinaria(Long idVeterinaria);
    List<VeterinariaProfesionalHorario> obtenerPorVeterinariaYHorario(Long idVeterinaria, LocalTime horario);
    void guardar(VeterinariaProfesionalHorario vph);
    List<VeterinariaProfesionalHorario> obtenerProfesionalesDeVeterinaria(Long idVet);
}
