package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

      private final Map<Long, Usuario> storage = new LinkedHashMap<>();
      private final AtomicInteger idGenerator = new AtomicInteger(1);
    
    // Constructor: algunos clientes hardcodeados para pruebas
    public ServicioUsuarioImpl() {
        Usuario c1 = new Usuario("Juan", "Perez", "juan.perez@mail.com", "1160000000");
        Usuario c2 = new Usuario("María", "González", "maria@mail.com", "1161111111");
        storage.put(c1.getId(), c1);
        storage.put(c2.getId(), c2);
    }

    private Integer nextId() {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {

        // Verifico si ya existe un cliente con el mismo correo
        boolean existeCorreo = storage.values().stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(usuario.getEmail()));

        if (existeCorreo) {
            throw new IllegalArgumentException("Ya existe un cliente con el correo: " + usuario.getEmail());
        }
        storage.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return storage.get(id);
    }

    @Override
    public void cancelarTurno(Usuario usuario, Integer id) {
        usuario.cancelarTurno(id);
    }

}
