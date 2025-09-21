package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioClienteImpl implements ServicioCliente {

      private final Map<Integer, Cliente> storage = new LinkedHashMap<>();  
      private final AtomicInteger idGenerator = new AtomicInteger(1);
    
    // Constructor: algunos clientes hardcodeados para pruebas
    public ServicioClienteImpl() {
        Cliente c1 = new Cliente(100, "Juan", "Perez", "juan.perez@mail.com", "1160000000");
        Cliente c2 = new Cliente(101, "María", "González", "maria@mail.com", "1161111111");
        storage.put(c1.getId(), c1);
        storage.put(c2.getId(), c2);
    }

    private Integer nextId() {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) {

        // Verifico si ya existe un cliente con el mismo correo
        boolean existeCorreo = storage.values().stream().anyMatch(c -> c.getCorreo().equalsIgnoreCase(cliente.getCorreo()));

        if (existeCorreo) {
            throw new IllegalArgumentException("Ya existe un cliente con el correo: " + cliente.getCorreo());
        }
        if (cliente.getId() == null) {
            cliente.setId(nextId());
        }
        storage.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Cliente buscarClientePorId(Integer id) {
        return storage.get(id);        
    }


}
