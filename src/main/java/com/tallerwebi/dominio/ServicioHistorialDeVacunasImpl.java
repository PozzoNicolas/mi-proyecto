package com.tallerwebi.dominio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioHistorialDeVacunasImpl implements ServicioHistorialDeVacunas {

    private final RepositorioHistorialDeVacunas repositorioHistorialDeVacunas;

    @Autowired
    public ServicioHistorialDeVacunasImpl(RepositorioHistorialDeVacunas repositorioHistorialDeVacunas) {
        this.repositorioHistorialDeVacunas = repositorioHistorialDeVacunas; 
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

    @Override
    public HistorialDeVacunas getPorId(Long id) {
        return repositorioHistorialDeVacunas.buscarPorId(id);
    }
}
