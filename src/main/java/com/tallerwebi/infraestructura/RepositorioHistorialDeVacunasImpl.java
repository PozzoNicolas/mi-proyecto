package com.tallerwebi.infraestructura;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.HistorialDeVacunas;
import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.RepositorioHistorialDeVacunas;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

@Repository("repositorioHistorialDeVacunas")
public class RepositorioHistorialDeVacunasImpl implements RepositorioHistorialDeVacunas {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioHistorialDeVacunasImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

     @Override
    public void guardar(HistorialDeVacunas historial) {
        final Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(historial);
    }

    @Override
    public HistorialDeVacunas buscarPorMascota(Mascota mascota) {
        final Session session = sessionFactory.getCurrentSession();
        String hql = "FROM HistorialDeVacunas h WHERE h.mascota = :mascota";
        return session.createQuery(hql, HistorialDeVacunas.class)
                      .setParameter("mascota", mascota)
                      .uniqueResult();
    }

    @Override
    public List<HistorialDeVacunas> listarPorUsuario(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        String hql = "FROM HistorialDeVacunas h WHERE h.mascota.duenio = :usuario";
        Query<HistorialDeVacunas> query = session.createQuery(hql, HistorialDeVacunas.class);
        query.setParameter("usuario", usuario);
        return query.list();
    }

    @Override
    public HistorialDeVacunas buscarPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return session.get(HistorialDeVacunas.class, id);
    }
    
}
