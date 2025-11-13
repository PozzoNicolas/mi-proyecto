package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServicioMail {

    private final ServicioVeterinaria servicioVeterinaria;
    private final JavaMailSender mailSender;

    @Autowired
    public ServicioMail(ServicioVeterinaria servicioVeterinaria, JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
        this.servicioVeterinaria = servicioVeterinaria;
    }

    @Async
    public void enviarMail(String para, String asunto, String cuerpo) {
        try {
            System.out.println("📨 Intentando enviar mail a: " + para);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(para);
            message.setSubject(asunto);
            message.setText(cuerpo);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    public void enviarConfirmacionDeTurno(Usuario usuario, Long turnoId, String direccion) {
        Turno turno = usuario.getTurnoPorId(turnoId);
        Veterinaria veterinaria = turno.getVeterinaria();
        
        String asunto = "Confirmación de turno - " + turno.getEspecialidad().getDesc();

        String cuerpo = String.format(
            "Estimado/a %s,%n%n" +
            "Le confirmamos su turno en la siguiente veterinaria:%n" +
            "- Veterinaria: %s%n" +
            "- Dirección: %s%n%n" +
            "Detalles del turno:%n" +
            "- Especialidad: %s%n" +
            "- Práctica: %s%n" +
            "- Fecha: %s%n" +
            "- Horario: %s%n" +
            "- Profesional: %s%n%n" +
            "Por favor, llegue 10 minutos antes del horario indicado.%n%n" +
            "¡Gracias por confiar en nosotros!%n%n" +
            "Atentamente,%n" +
            "VetConnect",
            usuario.getNombre(),
            veterinaria.getNombre(),
            veterinaria.getDireccion(),
            turno.getEspecialidad().getDesc(),
            turno.getPractica().getDesc(),
            turno.getFecha(),
            turno.getHorario(),
            turno.getProfesional()
        );

        enviarMail(direccion, asunto, cuerpo);
    }

    @Async
    public void enviarCancelacionDeTurno(Usuario usuario, Long turnoId, String direccion) {
        Turno turno = usuario.getTurnoPorId(turnoId);
        Veterinaria veterinaria = turno.getVeterinaria();

        String asunto = "Cancelación de turno - " + turno.getEspecialidad().getDesc();

        String cuerpo = String.format(
            "Estimado/a %s,%n%n" +
            "Le informamos que su turno ha sido cancelado:%n%n" +
            "- Veterinaria: %s%n" +
            "- Dirección: %s%n%n" +
            "Detalles del turno:%n" +
            "- Especialidad: %s%n" +
            "- Práctica: %s%n" +
            "- Fecha: %s%n" +
            "- Horario: %s%n" +
            "- Profesional: %s%n%n" +
            "Si tiene dudas o desea reprogramar, por favor contacte a la clínica.%n%n" +
            "Atentamente,%n" +
            "VetConnect",
            usuario.getNombre(),
            veterinaria.getNombre(),
            veterinaria.getDireccion(),
            turno.getEspecialidad().getDesc(),
            turno.getPractica().getDesc(),
            turno.getFecha(),
            turno.getHorario(),
            turno.getProfesional()
        );

        enviarMail(direccion, asunto, cuerpo);
    }

    @Async
    public void enviarRecordatorioDeCuidado(Usuario usuario) {
        String direccion = usuario.getEmail();

        String asunto = "Recordatorio de cuidado para tus mascotas";

        String cuerpo = String.format(
            "Hola %s,%n%n" +
            "Queríamos recordarte que una o más de tus mascotas podrían tener vacunas o cuidados pendientes.%n%n" +
            "Te recomendamos revisar su historial en VetConnect para asegurarte de que estén al día con sus vacunas y revisiones.%n%n" +
            "Mantener las vacunas y controles al día es clave para su salud y bienestar.%n%n" +
            "¡Gracias por confiar en nosotros para el cuidado de tus compañeros!%n%n" +
            "Atentamente,%n" +
            "El equipo de VetConnect"
            , usuario.getNombre()
        );

        enviarMail(direccion, asunto, cuerpo);
    }

    @Async
    public void enviarRecordatorioTest(Usuario usuario) {
                String direccion = usuario.getEmail();

        String asunto = "Recordatorio de cuidado para tus mascotas";

        String cuerpo = String.format(
            "Este es un test"
            , usuario.getNombre()
        );

        enviarMail(direccion, asunto, cuerpo);
    }

    @Async
    public void enviarRecordatorioProximoTurno(Turno turno) {
        Veterinaria veterinaria = turno.getVeterinaria();
        String direccion = turno.getUsuario().getEmail();
        Usuario usuario = turno.getUsuario();

        String asunto = "Recordatorio de turno - " + turno.getEspecialidad().getDesc();

        String cuerpo = String.format(
                "Estimado/a %s,%n%n" +
                        "Le recordamos su turno en la siguiente veterinaria:%n" +
                        "- Veterinaria: %s%n" +
                        "- Dirección: %s%n%n" +
                        "Detalles del turno:%n" +
                        "- Especialidad: %s%n" +
                        "- Práctica: %s%n" +
                        "- Fecha: %s%n" +
                        "- Horario: %s%n" +
                        "- Profesional: %s%n%n" +
                        "Por favor, llegue 10 minutos antes del horario indicado.%n%n" +
                        "¡Gracias por confiar en nosotros!%n%n" +
                        "Atentamente,%n" +
                        "VetConnect",
                usuario.getNombre(),
                veterinaria.getNombre(),
                veterinaria.getDireccion(),
                turno.getEspecialidad().getDesc(),
                turno.getPractica().getDesc(),
                turno.getFecha(),
                turno.getHorario(),
                turno.getProfesional()
        );

        enviarMail(direccion, asunto, cuerpo);
    }
}
