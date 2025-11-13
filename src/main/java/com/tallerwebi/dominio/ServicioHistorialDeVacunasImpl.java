package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.dominio.enums.Vacuna;

@Service
@Transactional
public class ServicioHistorialDeVacunasImpl implements ServicioHistorialDeVacunas {

    private final RepositorioHistorialDeVacunas repositorioHistorialDeVacunas;
    private final RepositorioMascota repositorioMascota;

    @Autowired
    public ServicioHistorialDeVacunasImpl(RepositorioHistorialDeVacunas repositorioHistorialDeVacunas, RepositorioMascota repositorioMascota) {
        this.repositorioHistorialDeVacunas = repositorioHistorialDeVacunas; 
        this.repositorioMascota = repositorioMascota;
    }

    @Override
    public void crearUnHistorialDeVacunas(HistorialDeVacunasDTO historialDTO) {
        HistorialDeVacunas historial = new HistorialDeVacunas();
        historial.setMascota(historialDTO.getMascota());
        historial.setVacunaciones(historialDTO.getVacunaciones());
        repositorioHistorialDeVacunas.guardar(historial);
    }

    @Override
    public HistorialDeVacunas getHistorialDeUnaMascota(Mascota mascota) {
        return repositorioHistorialDeVacunas.buscarPorMascota(mascota);
    }

    @Override
    public List<HistorialDeVacunas> listarTodosLosHistorialesDeUnUsuario(Usuario usuario) {
        return repositorioHistorialDeVacunas.listarPorUsuario(usuario);
    }

    @Transactional
    public HistorialDeVacunas getPorId(Long id) {
        HistorialDeVacunas h = repositorioHistorialDeVacunas.buscarPorId(id);
        if (h != null) {
            h.getVacunaciones().size(); 
        }
        return h;
    }

    @Override
    public void guardar(HistorialDeVacunas historial) {
        repositorioHistorialDeVacunas.guardar(historial);
    }

    @Override
    public List<Vacuna> getVacunasSinAplicar(Long id_mascota) {
        Mascota mascota = repositorioMascota.buscarMascotaPorId(id_mascota);
        HistorialDeVacunas historial = mascota.getHistorialDeVacunas();
        List<Vacunacion> vacunaciones = historial.getVacunaciones();

        List<Vacuna> sinAplicar = new ArrayList<>();

        for (Vacuna vacuna : Vacuna.values()) {
            boolean aplicada = false;

            for (Vacunacion vacunacionAplicada : vacunaciones) {
                if (vacunacionAplicada.getVacuna() == vacuna) {
                    aplicada = true;
                    break;
                }
            }

            if (!aplicada) {
                sinAplicar.add(vacuna);
            }
        }

        return sinAplicar;
    }

}
