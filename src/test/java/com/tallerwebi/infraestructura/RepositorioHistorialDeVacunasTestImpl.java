package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.Vacuna;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioHistorialDeVacunasTestImpl {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void deberiaGuardarYRecuperarUnHistorialDeVacunas() {

        RepositorioHistorialDeVacunasImpl repo =
                new RepositorioHistorialDeVacunasImpl(this.sessionFactory);

        // 1️⃣ Crear usuario y mascota
        Usuario usuario = new Usuario();
        usuario.setNombre("pepe");

        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setTipoDeMascota("Gato");
        mascota.setDuenio(usuario);

        // 2️⃣ Crear vacunación e historial
        Vacunacion vacunacion = new Vacunacion(Vacuna.RABIA_GATO, LocalDate.now());
        HistorialDeVacunas historial = new HistorialDeVacunas(mascota);
        historial.agregarVacunacion(vacunacion);

        // 3️⃣ Guardar en la base
        var session = sessionFactory.getCurrentSession();
        session.save(usuario);
        session.save(mascota);
        repo.guardar(historial);

        // 4️⃣ Recuperar por mascota
        HistorialDeVacunas obtenido = repo.buscarPorMascota(mascota);

        assertThat(obtenido, is(notNullValue()));
        assertThat(obtenido.getMascota().getNombre(), is("Luna"));
        assertThat(obtenido.getVacunaciones().size(), is(1));
    }

    @Test
    @Transactional
    public void deberiaListarLosHistorialesDeUnUsuario() {
        RepositorioHistorialDeVacunasImpl repo =
                new RepositorioHistorialDeVacunasImpl(this.sessionFactory);

        Usuario usuario = new Usuario();
        usuario.setNombre("pepe");

        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setTipoDeMascota("Gato");
        mascota.setDuenio(usuario);

        HistorialDeVacunas historial = new HistorialDeVacunas(mascota);

        var session = sessionFactory.getCurrentSession();
        session.save(usuario);
        session.save(mascota);
        session.save(historial);

        List<HistorialDeVacunas> historiales = repo.listarPorUsuario(usuario);

        assertThat(historiales, hasSize(1));
        assertThat(historiales.get(0).getMascota().getNombre(), is("Luna"));
    }
}
