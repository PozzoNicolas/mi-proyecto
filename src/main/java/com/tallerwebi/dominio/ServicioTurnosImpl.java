package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
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
            RepositorioVPH repositorioVPH) {
        this.servicioMail = servicioMail;
        this.repositorioTurnos = repositorioTurnos;
        this.repositorioVeterinaria = repositorioVeterinaria;
        this.repositorioProfesional = repositorioProfesional;
        this.repositorioVPH = repositorioVPH;
    }

    @Override
    public List<Veterinaria> listarVeterinariasIndiferente() {
        return repositorioVeterinaria.listarVeterinarias();
    }

    @Override
    public void guardarTurno(Usuario usuario, Turno turno) {
        if (repositorioTurnos.existeTurno(
                turno.getVeterinaria().getId(),
                turno.getProfesional().getDni(),
                turno.getFecha(),
                turno.getHorario())) {
            throw new IllegalStateException("El turno ya ha sido reservado.");
        }
        usuario.agregarTurno(turno);
        turno.setUsuario(usuario);
        repositorioTurnos.guardar(turno);
    }

    @Override
    public void procesarSeleccion(TurnoDTO turnoDTO) {
        if (turnoDTO.getSeleccion() == null || turnoDTO.getSeleccion().isEmpty())
            return;

        String[] partes = turnoDTO.getSeleccion().split("\\|\\|");

        if (partes.length != 3 || partes[0].isBlank() || partes[1].isBlank() || partes[2].isBlank()) {
            throw new IllegalArgumentException("Selección inválida: " + turnoDTO.getSeleccion());
        }

        try {
            // Primera parte veterinaria.
            int idVet = Integer.valueOf(partes[0]);
            turnoDTO.setVeterinariaId(idVet);

            // 2da parte horario. Queda como string
            // La traduccion de String a LocalTime se hace en crear turno con DTO
            String hora = partes[1];
            turnoDTO.setHorario(hora);

            // 3ra parte Profesional. Usar el nombre hace que no contemplemos
            // Dos prodesionales con el mismo nombre ojo
            Long idProf = Long.valueOf(partes[2]);
            turnoDTO.setProfesionalId(idProf);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("IDs inválidos en la selección: " + turnoDTO.getSeleccion(), e);
        }
    }

    @Scheduled(cron = "0 30 17 * * *")
    @Override
    public void envioReportesTurnosProximos() {
        System.out.println("Executing fixed rate method...");
        List<Turno> turnosAEnviar = repositorioTurnos.obtenerTurnosProximos();
        for (Turno turno : turnosAEnviar) {
            servicioMail.enviarRecordatorioProximoTurno(turno);

        }
    }

    @Override
public List<String> horariosDisponibles(Long idVet, LocalDate fecha) {
    List<LocalTime> horarios = horariosDeVeterinaria(idVet);

    return horarios.stream()
            .filter(h -> profesionalesDisponibles(idVet, fecha, h).size() > 0) // solo horarios con al menos un profesional libre
            .map(LocalTime::toString)
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
    public List<Profesional> profesionalesDisponibles(Long idVet, LocalDate fecha, LocalTime horario) {

        // 1️⃣ Traemos TODOS los VPH de esa veterinaria (ya con fetch join)
        List<VeterinariaProfesionalHorario> vphList = repositorioVPH.obtenerProfesionalesDeVeterinaria(idVet);

        // 2️⃣ Filtramos solo los del horario solicitado
        List<VeterinariaProfesionalHorario> vphFiltrados = vphList.stream()
                .filter(vph -> horario.equals(vph.getHorario()))
                .collect(Collectors.toList());

        // 3️⃣ Tomamos solo los profesionales que NO tienen turno en esa fecha y horario
        List<Profesional> disponibles = vphFiltrados.stream()
                .map(VeterinariaProfesionalHorario::getProfesional)
                .filter(prof -> !repositorioTurnos.existeTurno(idVet, prof.getDni(), fecha, horario))
                .collect(Collectors.toList());

        return disponibles;
    }

    public List<LocalTime> horariosDeVeterinaria(Long idVet) {
    // Traemos todos los VPH con profesionales ya cargados
    List<VeterinariaProfesionalHorario> vphList = repositorioVPH.obtenerProfesionalesDeVeterinaria(idVet);

    // Extraemos solo los horarios distintos
    return vphList.stream()
            .map(VeterinariaProfesionalHorario::getHorario)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
}

    // @Override
    // public List<Profesional> profesionalesDisponibles(Long idVet, LocalTime
    // horario) {
    // return repositorioVPH.obtenerPorVeterinariaYHorario(idVet, horario)
    // .stream()
    // .map(VeterinariaProfesionalHorario::getProfesional)
    // .distinct()
    // .collect(Collectors.toList());
    // }

    @Override
    public Veterinaria getVeterinariaPorTurnoDTO(TurnoDTO turnoDTO) {
        // If no vet was selected -> return an empty object
        if (turnoDTO.getVeterinariaId() == null) {
            return new Veterinaria();
        }

        Veterinaria v = repositorioVeterinaria.buscarPorId((long) turnoDTO.getVeterinariaId());
        return (v != null) ? v : new Veterinaria();
    }

    @Override
    public boolean esTurnoDTOEspPracValidas(TurnoDTO turnoDTO) {
        return turnoDTO.getEspecialidad() != null && turnoDTO.getPractica() != null;
    }

    @Override
    public Turno crearTurnoConDTO(TurnoDTO turnoDTO) {
        try {
            Turno turno = new Turno();

            turno.setEspecialidad(turnoDTO.getEspecialidad());
            turno.setPractica(turnoDTO.getPractica());

            Veterinaria vet = repositorioVeterinaria.buscarPorId((long) turnoDTO.getVeterinariaId());
            if (vet == null) {
                throw new IllegalArgumentException("Veterinaria no encontrada");
            }
            turno.setVeterinaria(vet);

            // Parse fecha y horario
            LocalDate fecha = LocalDate.parse(turnoDTO.getFecha());
            LocalTime horario = LocalTime.parse(turnoDTO.getHorario());
            turno.setFecha(fecha);
            turno.setHorario(horario);

            // Buscar profesional por nombre o ID
            Profesional profesional = repositorioProfesional.buscarPorId(turnoDTO.getProfesionalId());
            if (profesional == null) {
                throw new IllegalArgumentException("Profesional no encontrado");
            }
            turno.setProfesional(profesional);

            // usuario se setea en otro método (como dijiste)

            return turno;

        } catch (Exception e) {
            throw new IllegalArgumentException("El Dto contiene errores: " + e.getMessage(), e);
        }
    }

    public TurnoVistaDTO mapearTurnoATurnoVistaDTO(Turno turno) {
        TurnoVistaDTO dto = new TurnoVistaDTO();
        dto.setId(turno.getId());
        dto.setEspecialidad(turno.getEspecialidad().toString());
        dto.setPractica(turno.getPractica().toString());
        dto.setFecha(turno.getFecha());
        dto.setHorario(turno.getHorario());

        dto.setVeterinariaNombre(
                turno.getVeterinaria() != null ? turno.getVeterinaria().getNombre() : "N/A");

        dto.setProfesionalNombre(
                turno.getProfesional() != null ? turno.getProfesional().getNombre() : "N/A");

        return dto;
    }
}