package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTurnosImpl implements ServicioTurnos {
    
    private final ServicioVeterinaria servicioVeterinaria;

    @Autowired
    public ServicioTurnosImpl(ServicioVeterinaria servicioVeterinaria) {
        this.servicioVeterinaria = servicioVeterinaria; 
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
    public void guardarTurno(Cliente cliente, Turno turno) {
        cliente.getTurnos().add(turno); 
    }

    @Override
    public void procesarSeleccion(Turno turno) {
        if (turno.getSeleccion() != null) {
            String[] partes = turno.getSeleccion().split("\\|\\|");

            if (partes.length == 3) { // Caso "Indiferente"
                turno.setVeterinaria(Integer.parseInt(partes[0]));
                turno.setHorario(partes[1]);
                turno.setProfesional(partes[2]);
            } else if (partes.length == 2) { // Veterinaria específica
                turno.setHorario(partes[0]);
                turno.setProfesional(partes[1]);
            }
        }
    }
}
