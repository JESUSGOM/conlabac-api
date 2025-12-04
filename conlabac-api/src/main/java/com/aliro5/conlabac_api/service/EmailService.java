package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private EnvioEmailRepository envioEmailRepo;

    public void procesarIncidencia(Incidencia inc) {
        // Datos comunes del servidor
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        // Variables dinámicas según el centro (Replicando el PHP)
        String remitenteUser = "";
        String remitentePass = "Envera2025"; // Contraseña del PHP
        String destinatarioPrincipal = "";
        String nombreDestinatario = "";
        String asunto = "";
        String[] ccList = {};
        String bcc = "informaticaitc@jfgb.es";

        // LÓGICA DEL PHP TRADUCIDA
        if (inc.getIdCentro() == 1) { // Tenerife
            remitenteUser = "conserjeriaitc.tf@grupoenvera.org";
            destinatarioPrincipal = "cbetancor@itccanarias.org";
            nombreDestinatario = "María Carmen Betancor Reula";
            asunto = "Comunicación de Incidencia desde la conserjería del ITC en Tenerife.";
            // Lista de CC
            ccList = new String[]{"adelaida.gomez@grupoenvera.org"};
        }
        else if (inc.getIdCentro() == 2) { // Las Palmas
            remitenteUser = "conserjeriaitc.gc@grupoenvera.org";
            destinatarioPrincipal = "adominguez@itccanarias.org";
            nombreDestinatario = "Adriana Dominguez Sicilia";
            asunto = "Comunicación de Incidencia desde la conserjería del ITC en Las Palmas.";
            // Lista de CC
            ccList = new String[]{"josea.henriquez@grupoenvera.org", "jiglesias@itccanarias.org"};
        } else {
            System.out.println("Centro desconocido para envío de email: " + inc.getIdCentro());
            return;
        }

        // Configurar credenciales específicas
        mailSender.setUsername(remitenteUser);
        mailSender.setPassword(remitentePass);

        // 1. ENVIAR EL EMAIL
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remitenteUser, inc.getUsuario()); // Remitente + Nombre del vigilante
            helper.setTo(destinatarioPrincipal);
            helper.setSubject(asunto);
            helper.setText(inc.getTexto(), false); // false = texto plano, true = html
            helper.setBcc(bcc);

            if (ccList.length > 0) {
                helper.setCc(ccList);
            }
            // ReplyTo
            helper.setReplyTo(remitenteUser);

            mailSender.send(message);
            System.out.println("Email enviado correctamente a: " + destinatarioPrincipal);

            // 2. GUARDAR EN LA TABLA EnvioEmail (LOG)
            guardarLogEmail(inc, destinatarioPrincipal, remitenteUser);

        } catch (Exception e) {
            System.err.println("Error enviando email (Llamar a Jesús Gómez): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void guardarLogEmail(Incidencia inc, String destinatario, String emisor) {
        try {
            EnvioEmail log = new EnvioEmail();
            LocalDateTime ahora = LocalDateTime.now();

            log.setDestinatario(destinatario);
            log.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            log.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            log.setFechaHoraDt(ahora);
            log.setTexto(inc.getTexto());
            log.setEmisor(inc.getUsuario()); // Guardamos el nombre/usuario como en el PHP

            envioEmailRepo.save(log);
        } catch (Exception e) {
            System.err.println("Error guardando log de email: " + e.getMessage());
        }
    }
}