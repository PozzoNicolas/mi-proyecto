package com.tallerwebi.TDD;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

import java.time.LocalDate;
import java.time.LocalTime;

class ServicioMailTest {

    @Mock
    private ServicioVeterinaria servicioVeterinaria;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks //Para llamar a lo mockeado
    private ServicioMail servicioMail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enviarConfirmacionDeTurno_deberiaEnviarMail() {
        // Preparar datos
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");

        Profesional profesional = new Profesional("Dr. López", 123);

        Veterinaria vet = new Veterinaria();
        vet.setId(1L);
        vet.setNombre("VetUno");
        vet.setDireccion("Calle Falsa 123");

        Turno turno = new Turno();
        turno.setEspecialidad(Especialidad.CONTROL);
        turno.setPractica(Practica.CONTROL_1);
        turno.setFecha(LocalDate.of(2025, 10, 10));
        turno.setHorario(LocalTime.of(10, 0));
        turno.setProfesional(profesional);
        turno.setVeterinaria(vet);

        // assign id automatically and set back-reference
        usuario.agregarTurno(turno);

        // Configurar el mock ANTES de llamar al servicio
        when(servicioVeterinaria.buscarPorId(1L)).thenReturn(vet);

        // Ejecutar
        servicioMail.enviarConfirmacionDeTurno(usuario, turno.getId(), "juan@email.com");

        // Verificar que se llame a mailSender.send con algún SimpleMailMessage (solo una vez)
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void enviarCancelacionDeTurno_deberiaEnviarMail() {
        // Crear Veterinaria mock
        Veterinaria vet = new Veterinaria();
        vet.setId(1L);
        vet.setNombre("VetUno");
        vet.setDireccion("Calle Falsa 123");

        // Crear profesional
        Profesional profesional = new Profesional("Dra. Gómez", 456);

        // Crear usuario y turno
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");

        Turno turno = new Turno();
        turno.setEspecialidad(Especialidad.CONTROL);
        turno.setPractica(Practica.CONTROL_1);
        turno.setFecha(LocalDate.of(2025, 10, 12));
        turno.setHorario(LocalTime.of(15, 0));
        turno.setProfesional(profesional);
        turno.setVeterinaria(vet);

        // Agregar turno (assigns a proper ID)
        usuario.agregarTurno(turno);

        // Configurar mock ANTES de llamar al servicio
        when(servicioVeterinaria.buscarPorId(1L)).thenReturn(vet);

        // Ejecutar con ID real del turno
        servicioMail.enviarCancelacionDeTurno(usuario, turno.getId(), "ana@email.com");

        // Verificar envío de mail
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
