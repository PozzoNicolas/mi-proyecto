package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioVPHImpl implements ServicioVPH {

    private final RepositorioVPH repo;

    public ServicioVPHImpl(RepositorioVPH repo) {
        this.repo = repo;
    }

    public List<VeterinariaProfesionalHorario> obtenerProfesionalesDeVeterinaria(Long idVet) {
        return repo.obtenerPorVeterinaria(idVet);
    }

    public List<VeterinariaProfesionalHorario> obtenerProfesionalesEnHorario(Long idVet, LocalTime hora) {
        return repo.obtenerPorVeterinariaYHorario(idVet, hora);
    }

    public void asignarProfesionalHorario(VeterinariaProfesionalHorario vph) {
        repo.guardar(vph);
    }
}
