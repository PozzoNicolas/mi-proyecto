package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.RepositorioMascota;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMascotaImpl implements RepositorioMascota {

    private SessionFactory sessionFactory;

    @Autowired
    //Si usamos el autowired sobre el constructor tuviesemos más de un elemento, lo inyectaria a todos.
    public RepositorioMascotaImpl (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Mascota mascota) {
        this.sessionFactory.getCurrentSession().save(mascota);
    }

    @Override
    public Mascota buscarMascotaPorId(Long id) {
        Mascota mascota = this.sessionFactory.getCurrentSession().get(Mascota.class, id);
        return mascota;
    }

    @Override
    public void eliminarMascota(Mascota mascota) {
       mascota = buscarMascotaPorId(mascota.getId());
        if(mascota != null){
            this.sessionFactory.getCurrentSession().delete(mascota);
        }
    }

    @Override
    public List<Mascota> listarMascotas() {
        String hql = "from Mascota";
        return this.sessionFactory.getCurrentSession().createQuery(hql, Mascota.class).getResultList();
    }
}
