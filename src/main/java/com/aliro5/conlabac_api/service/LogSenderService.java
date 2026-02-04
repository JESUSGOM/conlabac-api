package com.aliro5.conlabac_api.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class LogSenderService {

    @Autowired
    @Qualifier("mailSenderTenerife")
    private JavaMailSender mailSender;

    /**
     * Se ejecuta automáticamente al arrancar la aplicación.
     * Pausa de 15 segundos para dar tiempo a que los ficheros de log se generen físicamente en W:
     */
    @EventListener(ApplicationReadyEvent.class)
    public void enviarLogsAlArranque() {
        System.out.println(">>> [LOG-SENDER] Detectado arranque. Iniciando pausa de 15s para reporte inicial...");
        try {
            Thread.sleep(15000);
            enviarLogsSedes();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Envío programado diario a las 23:55.
     */
    @Scheduled(cron = "0 55 23 * * *")
    public void enviarLogsSedes() {
        System.out.println(">>> [LOG-SENDER] Generando reporte de actividad para jesusfgomezb@gmail.com...");
        enviarLogPorSede("Tenerife", "tenerife-api.log");
        enviarLogPorSede("Gran Canaria", "grancanaria-api.log");
        // También podemos enviar el log general de la API que configuramos en application.properties
        enviarLogPorSede("General", "api-general.log");
    }

    private void enviarLogPorSede(String sede, String nombreArchivo) {
        // RUTA ABSOLUTA unificada con application.properties
        File logFile = new File("W:/PROYECTOS/Aliro5/logs/" + nombreArchivo);

        if (!logFile.exists() || logFile.length() == 0) {
            // No imprimimos error para no ensuciar la consola si el archivo aún no tiene datos
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true indica que es un mensaje multipart (con adjuntos)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // REMITENTE: Debe ser la cuenta autenticada de Tenerife
            helper.setFrom("conserjeriaitc.tf@grupoenvera.org");

            // DESTINATARIO: Tu cuenta de Gmail
            helper.setTo("jesusfgomezb@gmail.com");

            helper.setSubject("REPORTE ALIROS - Sede: " + sede);
            helper.setText("Se adjunta el reporte de actividad generado automáticamente para " + sede + ".");

            // Cargamos el recurso desde la unidad W:
            FileSystemResource file = new FileSystemResource(logFile);

            // Adjuntamos con extensión .txt para que Gmail no lo bloquee
            helper.addAttachment(nombreArchivo + ".txt", file);

            mailSender.send(message);
            System.out.println(">>> [LOG-SENDER] Reporte enviado con éxito: " + sede);

        } catch (Exception e) {
            System.err.println(">>> [LOG-SENDER] Error al enviar log de " + sede + ": " + e.getMessage());
        }
    }
}