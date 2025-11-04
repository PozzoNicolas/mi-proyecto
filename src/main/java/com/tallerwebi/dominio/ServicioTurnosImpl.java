package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class ServicioTurnosImpl implements ServicioTurnos {

    private final ServicioMail servicioMail;
    private final RepositorioTurnos repositorioTurnos;
    private final RepositorioVeterinaria repositorioVeterinaria;
    private final RepositorioProfesional repositorioProfesional;
    private final RepositorioVPH repositorioVPH;

    @Autowired
    public ServicioTurnosImpl(ServicioMail servicioMail,
                              RepositorioTurnos repositorioTurnos,
                              RepositorioVeterinaria repositorioVeterinaria,
                              RepositorioProfesional repositorioProfesional,
                              RepositorioVPH repositorioVPH
    ) {
        this.servicioMail = servicioMail;
        this.repositorioTurnos = repositorioTurnos;
        this.repositorioVeterinaria = repositorioVeterinaria;
        this.repositorioProfesional = repositorioProfesional;
        this.repositorioVPH = repositorioVPH;
    }

    @Override
    public boolean esTurnoValido(Turno turno) {
        return turno.getEspecialidad() != null && turno.getPractica() != null;
    }

    @Override
    public List<Veterinaria> listarVeterinariasIndiferente() {
        return repositorioVeterinaria.listarVeterinarias();
    }

    @Override
    public Veterinaria obtenerVeterinariaPorTurno(Turno turno) {
        Veterinaria v = repositorioVeterinaria.buscarPorId((long) turno.getIdVeterinariaBusqueda());
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

            turno.setVeterinaria(repositorioVeterinaria.buscarPorId(idVet));
            turno.setHorario(LocalTime.parse(horario));
            turno.setProfesional(repositorioProfesional.buscarPorDni(idProfesional));
        }

        // Caso 2 partes: horario || profesional
        else if (partes.length == 2) {

            String horario = partes[0];
            Integer idProfesional = Integer.parseInt(partes[1]);

            turno.setHorario(LocalTime.parse(horario));
            turno.setProfesional(repositorioProfesional.buscarPorDni(idProfesional));
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

    @Override
    public List<String> horariosDisponibles(Long idVet) {
        return repositorioVPH.obtenerPorVeterinaria(idVet)
                .stream()
                .map(vph -> vph.getHorario().toString())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<Profesional> profesionalesPorVeterinariaYHorario(Long idVet, LocalTime horario) {
        return repositorioVPH.obtenerPorVeterinariaYHorario(idVet, horario)
                .stream()
                .map(VeterinariaProfesionalHorario::getProfesional)
                .collect(Collectors.toList());
    }

    @Override
    public List<Profesional> profesionalesDisponibles(Long idVet, LocalTime horario) {
        return repositorioVPH.obtenerPorVeterinariaYHorario(idVet, horario)
                .stream()
                .map(VeterinariaProfesionalHorario::getProfesional)
                .distinct()
                .collect(Collectors.toList());
    }

}
