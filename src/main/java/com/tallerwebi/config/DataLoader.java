package com.tallerwebi.config;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalTime;

@Component
public class DataLoader {

    private final RepositorioVeterinaria repositorioVeterinaria;
    private final RepositorioProfesional repositorioProfesional;
    private final RepositorioVPH repositorioVPH;

    @Autowired
    public DataLoader(RepositorioVeterinaria repositorioVeterinaria,
                      RepositorioProfesional repositorioProfesional,
                      RepositorioVPH repositorioVPH) {
        this.repositorioVeterinaria = repositorioVeterinaria;
        this.repositorioProfesional = repositorioProfesional;
        this.repositorioVPH = repositorioVPH;
    }

    @PostConstruct
    @Transactional
    public void init() {
        // Solo cargar si no existe nada (evitar duplicados)
        if (repositorioVeterinaria.listarVeterinarias().isEmpty()) {
            Veterinaria v1 = new Veterinaria("Vet Uno", "Calle Falsa 123");
            Veterinaria v2 = new Veterinaria("Vete Dos", "Juan B. Justo");
            repositorioVeterinaria.guardar(v1);
            repositorioVeterinaria.guardar(v2);

            Profesional p1 = new Profesional("Ernesto Sabato", 111);
            Profesional p2 = new Profesional("H. G. Oesterheld", 222);
            Profesional p3 = new Profesional("Mariana Enriquez", 333);
            repositorioProfesional.guardar(p1);
            repositorioProfesional.guardar(p2);
            repositorioProfesional.guardar(p3);

            // Relaciones horario
            repositorioVPH.guardar(new VeterinariaProfesionalHorario(v1, p1, LocalTime.parse("10:00")));
            repositorioVPH.guardar(new VeterinariaProfesionalHorario(v1, p2, LocalTime.parse("10:00")));
            repositorioVPH.guardar(new VeterinariaProfesionalHorario(v1, p1, LocalTime.parse("11:00")));
            repositorioVPH.guardar(new VeterinariaProfesionalHorario(v2, p3, LocalTime.parse("14:00")));
            repositorioVPH.guardar(new VeterinariaProfesionalHorario(v2, p3, LocalTime.parse("15:00")));
        }
    }

}
