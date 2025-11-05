package com.tallerwebi.dominio;

import java.util.List;

import org.jboss.logging.Param;

import retrofit2.http.Query;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);

    Usuario buscarUsuarioPorId(Long id);

    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    void actualizar(Usuario usuario);

    List<Usuario> listarTodos();

    Usuario buscarPorId(Long id);

    void eliminar(Usuario usuario);

    void flush();
    
    Usuario buscarPorIdConTurnos(Long id);
}

