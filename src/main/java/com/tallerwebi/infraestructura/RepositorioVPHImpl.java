package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVPH;
import com.tallerwebi.dominio.VeterinariaProfesionalHorario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;


@Repository
@Transactional
public class RepositorioVPHImpl implements RepositorioVPH {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<VeterinariaProfesionalHorario> obtenerPorVeterinaria(Long idVeterinaria) {
        return sessionFactory.getCurrentSession()
            .createQuery(
                "select vph from VeterinariaProfesionalHorario vph " +
                "join fetch vph.profesional p " +  // ensures Profesional is loaded
                "where vph.veterinaria.id = :id order by vph.horario",
                VeterinariaProfesionalHorario.class)
            .setParameter("id", idVeterinaria)
            .list();
    }

    @Override
    public List<VeterinariaProfesionalHorario> obtenerPorVeterinariaYHorario(Long idVeterinaria, LocalTime horario) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "from VeterinariaProfesionalHorario vph where vph.veterinaria.id = :id and vph.horario = :h",
                        VeterinariaProfesionalHorario.class)
                .setParameter("id", idVeterinaria)
                .setParameter("h", horario)  // ahora horario es LocalTime
                .list();
    }

    @Override
    public void guardar(VeterinariaProfesionalHorario vph) {
        sessionFactory.getCurrentSession().save(vph);
    }

}
