package com.tallerwebi.dominio;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.enums.Vacuna;

@Service
public class ServicioSheduleMailImpl implements ServicioSheduleMail {

    private final ServicioMail servicioMail;
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioHistorialDeVacunas servicioHistorialDeVacunas;

    public ServicioSheduleMailImpl(ServicioMail servicioMail, RepositorioUsuario repositorioUsuario, ServicioHistorialDeVacunas servicioHistorialDeVacunas) {
        this.servicioMail = servicioMail; 
        this.repositorioUsuario = repositorioUsuario;
        this.servicioHistorialDeVacunas = servicioHistorialDeVacunas; 
    }

    @Override
    @Scheduled(cron = "0 0 20 * * 4") 
    public void enviarRecordatoriosDeMail() {
        //De momento se guia por usuario, para evitar enviar multiples
        //Mails a un usuario con multiples mascotas
        List<Usuario> usuarios = repositorioUsuario.listarTodos();

        for(Usuario usuario : usuarios) {
            List<Mascota> mascotas = usuario.getMascotas(); 
            boolean yaSeEnvioUnMailA = false;
            if(!yaSeEnvioUnMailA && mascotas != null) {
                for(Mascota mascota : mascotas) {
                    List<Vacuna> vacunasSinAplicar = servicioHistorialDeVacunas.getVacunasSinAplicar(mascota.getId());
                    if(!vacunasSinAplicar.isEmpty()) {
                        servicioMail.enviarRecordatorioDeCuidado(usuario); 
                        yaSeEnvioUnMailA = true;
                        break; 
                    }
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 */2 * * * *") 
    public void testRecordatorio() {
        System.out.println("Hola se deberia mandar un mail");
        servicioMail.enviarRecordatorioDeCuidado(repositorioUsuario.buscarPorId(1L));
        Usuario usuario = repositorioUsuario.buscarPorId(2L);
        servicioMail.enviarRecordatorioTest(usuario);
    }
}
