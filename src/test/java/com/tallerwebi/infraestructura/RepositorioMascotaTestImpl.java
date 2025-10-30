package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.RepositorioMascota;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

//Extender de Spring, para que nos deje tener acceso a ciertas anotaciones (ej. sessionFactory)
@ExtendWith(SpringExtension.class)

//Va a buscar el archivo donde se configuró la bdd.
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioMascotaTestImpl {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void deberiaGuardarUnaMascota() {

        RepositorioMascota repoMascota = new RepositorioMascotaImpl(this.sessionFactory);

        Mascota mascota = new Mascota("Luna", "Perro", "Pitbull", 8);

        repoMascota.guardar(mascota);

        String hql = "FROM Mascota WHERE id = :id";

        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);

        query.setParameter("id", mascota.getId());

        Mascota mascotaObtenida = (Mascota)query.getSingleResult();

        assertThat (mascotaObtenida, is(equalTo(mascota)));

    }

    @Test
    @Transactional
    public void buscarUnaMascotaSegunSuId (){

        RepositorioMascota repoMascota = new RepositorioMascotaImpl(this.sessionFactory);

        Mascota mascota = new Mascota ("Luna", "Perro", "Pitbull", 8);
        repoMascota.guardar(mascota);

        Mascota mascotaObtenida = repoMascota.buscarMascotaPorId(mascota.getId());

        assertThat (mascotaObtenida, is(equalTo(mascota)));
    }

    @Test
    @Transactional
    public void deberiaModificarUnAtributoDeLaMascotaGuardadEnLaBaseDeDatos (){

        RepositorioMascota repoMascota = new RepositorioMascotaImpl(this.sessionFactory);

        Mascota mascota = new Mascota ("Luna", "Perro", "Pitbull", 8);
        repoMascota.guardar(mascota);
        mascota.setEdad(9);

        Mascota mascotaObtenida = repoMascota.buscarMascotaPorId(mascota.getId());

        assertThat (mascotaObtenida.getEdad(), is(9));
    }

    @Test
    @Transactional
    public void deberiaEliminarUnaMascotaDeLaBaseDeDatos (){

        RepositorioMascota repoMascota = new RepositorioMascotaImpl(this.sessionFactory);

        Mascota mascota = new Mascota ("Luna", "Perro", "Pitbull", 8);
        repoMascota.guardar(mascota);

        repoMascota.eliminarMascota(mascota);
        Mascota mascotaObtenida = repoMascota.buscarMascotaPorId(mascota.getId());

        assertNull (mascotaObtenida);
    }

    @Test
    @Transactional
    public void deberiaListarTodasLasMascotasGuardadasEnLaBaseDeDatos (){

        RepositorioMascota repoMascota = new RepositorioMascotaImpl(this.sessionFactory);

        Mascota mascota1 = new Mascota ("Luna1", "Perro", "Pitbull", 8);
        Mascota mascota2 = new Mascota ("Luna2", "Perro", "Pitbull", 8);
        Mascota mascota3 = new Mascota ("Luna3", "Perro", "Pitbull", 8);
        repoMascota.guardar(mascota1);
        repoMascota.guardar(mascota2);
        repoMascota.guardar(mascota3);

        List <Mascota> listaDeMascotas = repoMascota.listarMascotas();

        assertThat (listaDeMascotas.size(), is(3));
        assertThat(listaDeMascotas, hasItems(mascota1, mascota2, mascota3));
    }

}
