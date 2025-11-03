package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTurnos;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public List<Turno> obtenerTurnosProximos() {
        Session session = this.sessionFactory.getCurrentSession();

        String hoy = LocalDate.now().toString();
        String limite = LocalDate.now().plusDays(5).toString();

        return  session.createCriteria(Turno.class)
                .add(Restrictions.between("fecha", hoy, limite)).list();
    }
}
