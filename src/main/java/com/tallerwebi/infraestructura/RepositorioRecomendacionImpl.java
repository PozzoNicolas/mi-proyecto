package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Recomendacion;
import com.tallerwebi.dominio.RepositorioRecomendacion;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RepositorioRecomendacionImpl implements RepositorioRecomendacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioRecomendacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarRecomendacion(Recomendacion recomendacion) {
        sessionFactory.getCurrentSession().saveOrUpdate(recomendacion);
    }

    @Override
    public List<Recomendacion> listarPorUsuario(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Recomendacion where usuario = :usuario", Recomendacion.class)
                .setParameter("usuario", usuario)
                .list();
    }
}