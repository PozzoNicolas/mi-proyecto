package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {

    @Autowired
    private com.tallerwebi.infraestructura.RepositorioUsuarioImpl repositorioUsuario;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setActivo(true);
        return repositorioUsuario.save(usuario);
    }

    public Usuario obtenerPorEmail(String email) {
        return repositorioUsuario.buscar(email);
    }

    public void actualizar(Usuario usuarioExistente) {

    }

}
