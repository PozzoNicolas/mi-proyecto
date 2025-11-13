package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.EnlaceAdopcion;
import com.tallerwebi.dominio.RepositorioEnlaceAdopcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RepositorioEnlaceAdopcionImpl implements RepositorioEnlaceAdopcion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEnlaceAdopcionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<EnlaceAdopcion> listarTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("from EnlaceAdopcion", EnlaceAdopcion.class)
                .list();
    }
}