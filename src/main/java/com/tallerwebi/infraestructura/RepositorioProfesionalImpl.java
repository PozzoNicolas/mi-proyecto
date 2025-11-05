package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.RepositorioProfesional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioProfesionalImpl implements RepositorioProfesional {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Profesional buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Profesional.class, id);
    }

    @Override
    public Profesional buscarPorDni(Integer dni) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Profesional p where p.dni = :dni", Profesional.class)
                .setParameter("dni", dni)
                .uniqueResult();
    }

    @Override
    public void guardar(Profesional profesional) {
        sessionFactory.getCurrentSession().save(profesional);
    }

    @Override
    public Profesional buscarPorNombre(String nombre) {
        return sessionFactory.getCurrentSession().get(Profesional.class, nombre);
    }
}
