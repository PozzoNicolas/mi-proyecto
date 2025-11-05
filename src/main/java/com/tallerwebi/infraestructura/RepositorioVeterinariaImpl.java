package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVeterinaria;
import com.tallerwebi.dominio.Veterinaria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioVeterinariaImpl implements RepositorioVeterinaria {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Veterinaria buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Veterinaria.class, id);
    }

    @Override
    public List<Veterinaria> listarVeterinarias() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Veterinaria", Veterinaria.class)
                .list();
    }

    @Override
    public void guardar(Veterinaria veterinaria) {
        sessionFactory.getCurrentSession().save(veterinaria);
    }


}
