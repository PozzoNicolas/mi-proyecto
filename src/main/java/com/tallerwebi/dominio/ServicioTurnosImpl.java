package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@EnableScheduling
public class ServicioTurnosImpl implements ServicioTurnos {
    
    private final ServicioVeterinaria servicioVeterinaria;
    private final RepositorioTurnos repositorioTurnos;
    private final ServicioMail servicioMail;
    private final ServicioProfesional servicioProfesional;

    @Autowired
    public ServicioTurnosImpl(ServicioVeterinaria servicioVeterinaria, RepositorioTurnos repositorioTurnos, ServicioMail servicioMail, ServicioProfesional servicioProfesional) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.repositorioTurnos = repositorioTurnos;
        this.servicioMail = servicioMail;
        this.servicioProfesional = servicioProfesional;
    }

    @Override
    public boolean esTurnoValido(Turno turno) {
        return turno.getEspecialidad() != null && turno.getPractica() != null;
    }

    @Override
    public List<Veterinaria> listarVeterinariasIndiferente() {
        return servicioVeterinaria.listarVeterinarias();
    }

    @Override
    public Veterinaria obtenerVeterinariaPorTurno(Turno turno) {
        Veterinaria v = turno.getVeterinaria();
        return (v != null) ? v : new Veterinaria();
    }

    @Override
    public void guardarTurno(Usuario usuario, Turno turno) {

        usuario.agregarTurno(turno);
        turno.setUsuario(usuario);
        repositorioTurnos.guardar(turno);
    }


    @Override
    public void procesarSeleccion(Turno turno) {
        if (turno.getSeleccion() == null) return;

        String[] partes = turno.getSeleccion().split("\\|\\|");

        // Caso 3 partes: veterinaria || horario || profesional
        if (partes.length == 3) {

            Long idVet = Long.parseLong(partes[0]);
            String horario = partes[1];
            Integer idProfesional = Integer.parseInt(partes[2]);

            turno.setVeterinaria(servicioVeterinaria.buscarPorId(idVet));
            turno.setHorario(LocalTime.parse(horario));
            turno.setProfesional(servicioProfesional.buscarPorDni(idProfesional));
        }

        // Caso 2 partes: horario || profesional
        else if (partes.length == 2) {

            String horario = partes[0];
            Integer idProfesional = Integer.parseInt(partes[1]);

            turno.setHorario(LocalTime.parse(horario));
            turno.setProfesional(servicioProfesional.buscarPorDni(idProfesional));
        }
    }

    @Scheduled(cron = "0 30 17 * * *")
    @Override
    public void envioReportesTurnosProximos() {
        System.out.println("Executing fixed rate method...");
        List<Turno> turnosAEnviar =  repositorioTurnos.obtenerTurnosProximos();
        for (Turno turno : turnosAEnviar) {
            servicioMail.enviarRecordatorioProximoTurno(turno);

        }
    }
}
