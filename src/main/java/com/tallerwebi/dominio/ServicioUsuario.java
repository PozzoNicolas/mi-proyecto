package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ServicioUsuario {

    @Autowired
    private com.tallerwebi.infraestructura.RepositorioUsuarioImpl repositorioUsuario;

    public abstract Usuario buscarPorEmail(String email);

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setActivo(true);
        return repositorioUsuario.save(usuario);
    }

    public Usuario obtenerPorEmail(String email) {
        return repositorioUsuario.buscar(email);
    }

    public void actualizar(Usuario usuarioExistente) {

    }

    public abstract void actualizarUsuario(Usuario usuario);

    public abstract List<Usuario> listarUsuarios();

    public abstract void eliminarUsuario(Long id);
}
