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

    @Override
    public List<VeterinariaProfesionalHorario> obtenerPorVeterinaria(Long idVeterinaria) {
        List<VeterinariaProfesionalHorario> vphList = repo.obtenerPorVeterinaria(idVeterinaria);

        // ensure profesionales are initialized
        for (VeterinariaProfesionalHorario vph : vphList) {
            if (vph.getProfesional() != null) {
                vph.getProfesional().getNombre();
            }
        }

        return vphList;
    }
}
