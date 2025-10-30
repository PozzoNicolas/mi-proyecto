package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        Usuario usuario = repositorioUsuario.buscarUsuario(email, password);
        if (usuario != null){
            usuario.getMascotas().size();
        }
        return usuario;
    }

    @Override

    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);

        // Forzamos que Hibernate sincronice con la base y obtenga el ID
        repositorioUsuario.flush();

        System.out.println("✅ Usuario registrado con ID: " + usuario.getId() + " | Email: " + usuario.getEmail());
    }

}

