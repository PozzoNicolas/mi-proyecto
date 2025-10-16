package com.tallerwebi.TDD;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.ServicioVeterinaria;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Veterinaria;
import com.tallerwebi.dominio.enums.Especialidad;
import com.tallerwebi.dominio.enums.Practica;

class ServicioMailTest {

    @Mock
    private ServicioVeterinaria servicioVeterinaria;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private ServicioMail servicioMail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enviarConfirmacionDeTurno_deberiaEnviarMail() {
        // Preparar datos
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        Turno turno = new Turno();
        turno.setId(1);
        turno.setEspecialidad(Especialidad.CONTROL);
        turno.setPractica(Practica.CONTROL_1);
        turno.setFecha("10/10/2025");
        turno.setHorario("10:00");
        turno.setProfesional("Dr. López");
        turno.setVeterinaria(1);
        cliente.getTurnos().add(turno);

        Veterinaria vet = new Veterinaria();
        vet.setId(1);
        vet.setNombre("VetUno");
        vet.setDireccion("Calle Falsa 123");

        when(servicioVeterinaria.buscarPorId(1)).thenReturn(vet);

        // Ejecutar
        servicioMail.enviarConfirmacionDeTurno(cliente, 1, "juan@email.com");

        // Verificar que se llame a mailSender.send con algún SimpleMailMessage
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void enviarCancelacionDeTurno_deberiaEnviarMail() {
        // Preparar datos igual que arriba
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana");
        Turno turno = new Turno();
        turno.setId(2);
        turno.setEspecialidad(Especialidad.CONTROL);
        turno.setPractica(Practica.CONTROL_1);        turno.setFecha("12/10/2025");
        turno.setHorario("15:00");
        turno.setProfesional("Dra. Gómez");
        turno.setVeterinaria(2);
        cliente.getTurnos().add(turno);

        Veterinaria vet = new Veterinaria();
        vet.setId(2);
        vet.setNombre("VetDos");
        vet.setDireccion("Av. Siempre Viva 742");

        when(servicioVeterinaria.buscarPorId(2)).thenReturn(vet);

        // Ejecutar
        servicioMail.enviarCancelacionDeTurno(cliente, 2, "ana@email.com");

        // Verificar envío
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
