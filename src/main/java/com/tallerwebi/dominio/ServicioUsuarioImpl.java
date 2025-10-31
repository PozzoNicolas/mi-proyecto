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
        c1.setId(1L);
        c2.setId(2L);
        storage.put(c1.getId(), c1);
        storage.put(c2.getId(), c2);
        idGenerator.set(3);

    }

    private Long nextId() {
        return (long) idGenerator.getAndIncrement(); // Convertir el Integer generado a Long
    }
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // ...
        // Asumiendo que el constructor de Usuario no asignó el ID:
        if (usuario.getId() == null || usuario.getId() == 0) {
            usuario.setId(nextId());
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
