package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTurnos;
import com.tallerwebi.dominio.Turno;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class RepositorioTurnosImpl implements RepositorioTurnos {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioTurnosImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Turno turno) {
        this.sessionFactory.getCurrentSession().save(turno);
    }

    @Override
    public List<Turno> obtenerTurnosPorVeterinariaYFecha(Long vetId, LocalDate fecha) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "from Turno t where t.veterinaria.id = :vetId and t.fecha = :fecha",
                        Turno.class)
                .setParameter("vetId", vetId)
                .setParameter("fecha", fecha)
                .list();
    }

    @Override
    public List<Turno> obtenerTurnosProximos() {
        LocalDate hoy = LocalDate.now();
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "from Turno t where t.fecha >= :hoy order by t.fecha asc",
                        Turno.class)
                .setParameter("hoy", hoy)
                .list();
    }
}
