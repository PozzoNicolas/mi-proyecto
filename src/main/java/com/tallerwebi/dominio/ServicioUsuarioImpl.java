package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final Map<Long, Usuario> storage = new LinkedHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final RepositorioUsuario repositorioUsuario;

    // ✅ Constructor con inyección automática del repositorio
    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;

        // Datos de prueba opcionales
        Usuario c1 = new Usuario("Juan", "Perez", "juan.perez@mail.com", "1160000000");
        Usuario c2 = new Usuario("María", "González", "maria@mail.com", "1161111111");
        c1.setId(1L);
        c2.setId(2L);
        storage.put(c1.getId(), c1);
        storage.put(c2.getId(), c2);
        idGenerator.set(3);
    }

    public ServicioUsuarioImpl() {
        this.repositorioUsuario = null;
    }

    // Genera IDs incrementales
    private Long nextId() {
        return (long) idGenerator.getAndIncrement();
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // ✅ Check for duplicate email
        boolean emailExists = storage.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException(
                "Ya existe un cliente con el correo: " + usuario.getEmail()
            );
        }

        // Generate ID if necessary
        if (usuario.getId() == null || usuario.getId() == 0) {
            usuario.setId(nextId());
        }

        storage.put(usuario.getId(), usuario);

        // Save in DB if repository exists
        try {
            if (repositorioUsuario != null) {
                repositorioUsuario.guardar(usuario);
            }
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo guardar en base de datos (modo prueba): " + e.getMessage());
        }

        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void cancelarTurno(Usuario usuario, Long id) {
        usuario.cancelarTurno(id);
    }

    @Transactional
    public Usuario buscarUsuarioPorId(Long id) {
        Usuario usuario = storage.get(id);
        if (usuario == null && repositorioUsuario != null) {
            usuario = repositorioUsuario.buscarPorId(id);
            if (usuario != null) {
                usuario.getTurnos().size(); // Initialize lazy collection safely
            }
        }
        return usuario;
    }

}
