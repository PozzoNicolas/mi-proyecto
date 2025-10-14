package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCliente {

    Cliente registrarCliente(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente buscarClientePorId(Integer id);
    void cancelarTurno(Cliente cliente,Integer id); 
}
