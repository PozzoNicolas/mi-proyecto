package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioUsuarioImpl extends ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        return repositorioUsuario.buscar(email);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        Usuario existente = repositorioUsuario.buscar(usuario.getEmail());
        if (existente != null) {
            throw new RuntimeException("Ya existe un usuario con ese correo electrónico");
        }

        repositorioUsuario.guardar(usuario);
        return null;
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Datos de usuario inválidos");
        }
        repositorioUsuario.actualizar(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.listarTodos();
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id);
        if (usuario != null) {
            repositorioUsuario.eliminar(usuario);
        }
    }
}
