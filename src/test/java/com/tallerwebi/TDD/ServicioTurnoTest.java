package com.tallerwebi.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioTurnosImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ServicioTurnoTest {

    private ServicioTurnosImpl servicioTurnos;

    @Mock
    private ServicioVeterinaria servicioVeterinaria;

    @Mock
    private ServicioProfesional servicioProfesional;

    @Mock
    private RepositorioTurnos repositorioTurnos;

    @Mock
    private ServicioMail servicioMail;

    @Mock
    private RepositorioVeterinaria repositorioVeterinaria;

    @Mock
    private RepositorioProfesional repositorioProfesional;

    @Mock
    private RepositorioVPH repositorioVPH;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa todos los @Mock
        servicioTurnos = new ServicioTurnosImpl(
                servicioMail,
                repositorioTurnos,
                repositorioVeterinaria,
                repositorioProfesional,
                repositorioVPH
        );
    }


    @Test
    public void debeDebolverTrueCuandoLaEspYPractDeUnTurnoDtoSeanValidas() {
        TurnoDTO turnoDTO = new TurnoDTO();

        turnoDTO.setEspecialidad(Especialidad.ESTUDIO);
        turnoDTO.setPractica(Practica.ESTUDIO_1);
        assertTrue(servicioTurnos.esTurnoDTOEspPracValidas(turnoDTO)); 
    }

    @Test
    public void devolverFalseAlBuscarUnTurnoSinEspecialidad() {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setPractica(Practica.VACUNA_1);
        assertFalse(servicioTurnos.esTurnoDTOEspPracValidas(turnoDTO)); 
    }

    @Test 
    public void devolverFalseAlBuscarUnTurnoSinPractica() {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setEspecialidad(Especialidad.ESTUDIO);
        assertFalse(servicioTurnos.esTurnoDTOEspPracValidas(turnoDTO));
    }

    @Test 
    public void queAlListarVeterinariasConUnaBusquedaIndiferenteObtengoUnaListaDeTodasLasVeterinariasRegistradas() {
        List<Veterinaria> veterinariasEsperadas = servicioVeterinaria.listarVeterinarias();
        List<Veterinaria> veterinariasBuscadas = servicioTurnos.listarVeterinariasIndiferente();

        int i = 0;
        while (i < veterinariasBuscadas.size()) {
            Veterinaria esperada = veterinariasEsperadas.get(i);
            Veterinaria buscada = veterinariasBuscadas.get(i);

            assertEquals(esperada.getNombre(), buscada.getNombre());
            assertEquals(esperada.getDireccion(), buscada.getDireccion());
            i++;
        }
    }

    @Test
    public void queAlHacerUnaBusquedaParaUnaVeterinariaElTurnoTransformeElIdDeBusquedaEnLaVeterinariaEsperada() {

        Veterinaria veterinariaEsperada = new Veterinaria("Vet Uno", "Calle 123");
        veterinariaEsperada.setId(1L);

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setVeterinariaId(1);

        when(repositorioVeterinaria.buscarPorId(1L))
            .thenReturn(veterinariaEsperada);

        Veterinaria veterinariaObtenida = servicioTurnos.getVeterinariaPorTurnoDTO(turnoDTO);

        assertEquals(veterinariaEsperada, veterinariaObtenida);
    }

    @Test
    public void queAlHacerUnaBusquedaSinUnaVeterinariaEspecificaObtengaUnObjetoVeterinariaVacio() {

        TurnoDTO turnoDTO = new TurnoDTO(); 
        turnoDTO.setVeterinariaId(null);
        Veterinaria v = servicioTurnos.getVeterinariaPorTurnoDTO(turnoDTO);

        assertNotNull(v);
        assertNull(v.getNombre());
        assertNull(v.getDireccion());
        assertTrue(v.getProfesionalesEnHorario() == null || v.getProfesionalesEnHorario().isEmpty());
    }


    @Test
    public void queElServicioSeaCapazDeGuardarElTurnoEnUnUsuarioDado() {
        Usuario usuario = new Usuario("Nicolas", "Pozzo","nico@gmail.com","1155225522");
        Turno turno = new Turno();
        turno.setHorario(LocalTime.parse("10:00"));

        servicioTurnos.guardarTurno(usuario, turno);

        assertEquals(1, usuario.getTurnos().size());
        assertEquals(LocalTime.parse("10:00"), usuario.getTurnos().get(0).getHorario());
        verify(repositorioTurnos).guardar(turno); // comprueba que se llamó al repositorio
    }
    /*
    @Test
    public void queElServicioSeaCapazDeProcesarSeleccionConTresPartes() {
        Turno turno = new Turno();
        turno.setSeleccion("1||10:00||222");

        Veterinaria vet = new Veterinaria();
        vet.setId(1L);

        Profesional prof = new Profesional();
        prof.setDni(222);

        when(repositorioVeterinaria.buscarPorId(1L)).thenReturn(vet);
        when(repositorioProfesional.buscarPorDni(222)).thenReturn(prof);

        servicioTurnos.procesarSeleccion(turno);

        assertEquals(1L, turno.getVeterinaria().getId());
        assertEquals(LocalTime.parse("10:00"), turno.getHorario());
        assertEquals(222, turno.getProfesional().getDni());
    }  */
    
}
