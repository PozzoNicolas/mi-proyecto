package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioUsuario {

    Usuario registrarUsuario(Usuario usuario);
    List<Usuario> listarTodos();
    Usuario buscarUsuarioPorId(Long id);
    void cancelarTurno(Usuario usuario, Long id);
    Usuario buscarUsuarioPorIdConTurnos(Long id);
    void actualizar(Usuario usuairo); 
}
