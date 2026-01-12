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
     * @Async hace que este método se ejecute en un hilo separado.
     * El usuario recibe la confirmación de la incidencia sin esperar al SMTP.
     */
    @Async
    public void procesarIncidencia(Incidencia inc) {
        // 1. CONFIGURACIÓN DINÁMICA DEL EMISOR (Basado en tu lógica PHP)
        String remitenteUser = "";
        String remitentePass = "Envera2025";
        String destinatarioPrincipal = "";
        String asunto = "";
        String[] ccList = {};
        String bcc = "informaticaitc@jfgb.es";

        if (inc.getIdCentro() == 1) { // Tenerife
            remitenteUser = "conserjeriaitc.tf@grupoenvera.org";
            destinatarioPrincipal = "cbetancor@itccanarias.org";
            asunto = "Comunicación de Incidencia desde la conserjería del ITC en Tenerife.";
            ccList = new String[]{"adelaida.gomez@grupoenvera.org"};
        }
        else if (inc.getIdCentro() == 2) { // Las Palmas
            remitenteUser = "conserjeriaitc.gc@grupoenvera.org";
            destinatarioPrincipal = "adominguez@itccanarias.org";
            asunto = "Comunicación de Incidencia desde la conserjería del ITC en Las Palmas.";
            ccList = new String[]{"josea.henriquez@grupoenvera.org", "jiglesias@itccanarias.org"};
        } else {
            return;
        }

        // 2. CONFIGURAR EL SENDER DE SPRING
        JavaMailSenderImpl mailSender = configurarMailSender(remitenteUser, remitentePass);

        // 3. ENVIAR EL EMAIL
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remitenteUser, inc.getUsuario());
            helper.setTo(destinatarioPrincipal);
            helper.setSubject(asunto);
            helper.setText(inc.getTexto(), false);
            helper.setBcc(bcc);
            helper.setReplyTo(remitenteUser);

            if (ccList.length > 0) {
                helper.setCc(ccList);
            }

            mailSender.send(message);

            // 4. GUARDAR LOG (Solo si el envío fue exitoso)
            guardarLogEmail(inc, destinatarioPrincipal);

        } catch (Exception e) {
            System.err.println("CRÍTICO: Error enviando email de incidencia: " + e.getMessage());
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