package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private EnvioEmailRepository envioEmailRepo;

    /**
     * @Async ejecuta el envío en segundo plano.
     * Ahora tanto el usuario como la contraseña se definen por centro.
     */
    @Async
    public void procesarIncidencia(Incidencia inc) {
        // --- VARIABLES DINÁMICAS ---
        String remitenteUser = "";
        String remitentePass = ""; // Se asignará individualmente
        String nombreExposicion = "";
        String destinatarioPrincipal = "";
        String asunto = "";
        String centroNombre = "";
        String[] ccList = {};
        String bcc = "informaticaitc@jfgb.es";

        // --- ASIGNACIÓN DE CREDENCIALES Y DATOS POR CENTRO ---
        if (inc.getIdCentro() == 1) { // Tenerife
            centroNombre = "Tenerife";
            remitenteUser = "conserjeriaitc.tf@grupoenvera.org";
            remitentePass = "envera2026"; // <--- Cambia aquí si es distinta
            nombreExposicion = "Conserjería ITC Tenerife";
            destinatarioPrincipal = "cbetancor@itccanarias.org";
            asunto = "Nueva Incidencia - ITC Tenerife";
            ccList = new String[]{"gloria.santana@grupoenvera.org"};
        }
        else if (inc.getIdCentro() == 2) { // Las Palmas
            centroNombre = "Las Palmas";
            remitenteUser = "conserjeriaitc.gc@grupoenvera.org";
            remitentePass = "Envera2026"; // <--- Cambia aquí si es distinta
            nombreExposicion = "Conserjería ITC Las Palmas";
            destinatarioPrincipal = "dgi_cebrian@itccanarias.org";
            asunto = "Nueva Incidencia - ITC Las Palmas";
            ccList = new String[]{"josea.henriquez@grupoenvera.org", "jiglesias@itccanarias.org"};
        }
        else {
            System.err.println("EMAIL: No se envía correo, ID de centro desconocido: " + inc.getIdCentro());
            return;
        }

        // --- CONFIGURACIÓN DEL SENDER CON LOS DATOS ESPECÍFICOS DEL CENTRO ---
        JavaMailSenderImpl mailSender = configurarMailSender(remitenteUser, remitentePass);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remitenteUser, nombreExposicion);
            helper.setTo(destinatarioPrincipal);
            helper.setSubject(asunto);
            helper.setBcc(bcc);
            helper.setReplyTo(remitenteUser);

            if (ccList.length > 0) {
                helper.setCc(ccList);
            }

            // Cuerpo del mensaje HTML
            String cuerpoHtml = String.format(
                    "<html><body style='font-family: sans-serif;'>" +
                            "<h2 style='color: #2c3e50;'>Comunicación de Incidencia</h2>" +
                            "<p>Se ha registrado una incidencia en la conserjería:</p>" +
                            "<ul>" +
                            "  <li><b>Centro:</b> %s</li>" +
                            "  <li><b>Vigilante:</b> %s</li>" +
                            "  <li><b>Fecha/Hora:</b> %s</li>" +
                            "</ul>" +
                            "<p><b>Descripción:</b></p>" +
                            "<div style='padding: 15px; background-color: #f2f2f2; border-left: 5px solid #3498db;'>%s</div>" +
                            "</body></html>",
                    centroNombre,
                    inc.getUsuario(),
                    inc.getFechaHora() != null ? inc.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : inc.getFecha(),
                    inc.getTexto().replace("\n", "<br>")
            );

            helper.setText(cuerpoHtml, true);

            mailSender.send(message);
            guardarLogEmail(inc, destinatarioPrincipal);

        } catch (Exception e) {
            System.err.println("CRÍTICO: Error enviando email desde " + centroNombre + " (" + remitenteUser + "): " + e.getMessage());
        }
    }

    private JavaMailSenderImpl configurarMailSender(String user, String pass) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername(user);
        mailSender.setPassword(pass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        return mailSender;
    }

    private void guardarLogEmail(Incidencia inc, String destinatario) {
        try {
            EnvioEmail log = new EnvioEmail();
            LocalDateTime ahora = LocalDateTime.now();
            log.setDestinatario(destinatario);
            log.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            log.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            log.setFechaHoraDt(ahora);
            log.setTexto(inc.getTexto());
            log.setEmisor(inc.getUsuario());
            envioEmailRepo.save(log);
        } catch (Exception e) {
            System.err.println("Error guardando log de email: " + e.getMessage());
        }
    }
}