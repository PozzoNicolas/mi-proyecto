package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class ServicioTurnosImpl implements ServicioTurnos {
    
    private final ServicioVeterinaria servicioVeterinaria;
    private final RepositorioTurnos repositorioTurnos;
    private final ServicioMail servicioMail;

    @Autowired
    public ServicioTurnosImpl(ServicioVeterinaria servicioVeterinaria, RepositorioTurnos repositorioTurnos, ServicioMail servicioMail) {
        this.servicioVeterinaria = servicioVeterinaria;
        this.repositorioTurnos = repositorioTurnos;
        this.servicioMail = servicioMail;
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
        Veterinaria v = servicioVeterinaria.buscarPorId(turno.getVeterinaria());
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
        if (turno.getSeleccion() != null) {
            String[] partes = turno.getSeleccion().split("\\|\\|");

            if (partes.length == 3) { // Caso "Indiferente"
                turno.setVeterinaria(Long.parseLong(partes[0]));
                turno.setHorario(partes[1]);
                turno.setProfesional(partes[2]);
            } else if (partes.length == 2) { // Veterinaria específica
                turno.setHorario(partes[0]);
                turno.setProfesional(partes[1]);
            }
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
