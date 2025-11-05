package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }



    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public void actualizar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Usuario.class).list();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.get(Usuario.class, id);
    }


    @Override
    public void eliminar(Usuario usuario) {

    }

    public Usuario save(Usuario usuario) {
        return usuario;
    }

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Usuario buscarPorIdConTurnos(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "SELECT DISTINCT u FROM Usuario u " +
            "LEFT JOIN FETCH u.turnos t " +
            "LEFT JOIN FETCH t.veterinaria " +
            "LEFT JOIN FETCH t.profesional " +
            "WHERE u.id = :id", Usuario.class)
            .setParameter("id", id)
            .uniqueResult();
    }
}
