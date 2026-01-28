package com.aliro5.conlabac_api.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JavaMailSender mailSender;

    /**
     * Se ejecuta automáticamente al arrancar la aplicación.
     * Esperamos 10 segundos para asegurar que el sistema de logs haya inicializado los archivos.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void enviarLogsAlArranque() {
        System.out.println(">>> [LOG-SENDER] Detectado arranque. Iniciando pausa de 10s para auto-envío...");
        try {
            Thread.sleep(10000);
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
        System.out.println(">>> [LOG-SENDER] Iniciando proceso de envío a jesusfgomezb@gmail.com...");
        enviarLogPorSede("Tenerife", "tenerife-api.log");
        enviarLogPorSede("Gran Canaria", "grancanaria-api.log");
    }

    private void enviarLogPorSede(String sede, String nombreArchivo) {
        // Buscamos el archivo en la carpeta de logs relativa al ejecutable
        File logFile = new File("./logs/" + nombreArchivo);

        if (!logFile.exists()) {
            System.out.println(">>> [LOG-SENDER] INFO: El archivo " + nombreArchivo + " no existe aún. Se saltará este envío.");
            return;
        }

        if (logFile.length() == 0) {
            System.out.println(">>> [LOG-SENDER] INFO: El archivo " + nombreArchivo + " está vacío. No hay actividad que enviar.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true para permitir adjuntos, UTF-8 para tildes y caracteres especiales
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // CONFIGURACIÓN DE AUTO-ENVÍO (Gmail requiere que From y To sean iguales si usas tu propia cuenta)
            helper.setFrom("jesusfgomezb@gmail.com");
            helper.setTo("jesusfgomezb@gmail.com");
            helper.setSubject("AUTO-LOG ALIROS - Sede: " + sede);
            helper.setText("Reporte automático de actividad detectado en el archivo de logs de " + sede + ".");

            FileSystemResource file = new FileSystemResource(logFile);
            // Adjuntamos como .txt para evitar que Gmail lo bloquee por seguridad
            helper.addAttachment("log-" + sede.toLowerCase().replace(" ", "") + ".txt", file);

            mailSender.send(message);
            System.out.println(">>> [LOG-SENDER] ¡AUTO-ENVÍO EXITOSO! Revisar bandeja de entrada para: " + sede);

        } catch (Exception e) {
            System.err.println(">>> [LOG-SENDER] ERROR CRÍTICO: No se pudo realizar el auto-envío. Motivo: " + e.getMessage());
            // Si el error persiste como 'Authentication failed', revisa la contraseña de aplicación de Google.
        }
    }
}