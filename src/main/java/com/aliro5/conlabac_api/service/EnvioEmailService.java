package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.model.dto.EmailRequestDTO;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EnvioEmailService {

    private static final Logger log = LoggerFactory.getLogger(EnvioEmailService.class);

    @Autowired private EnvioEmailRepository emailRepo;
    @Autowired private IncidenciaRepository incidenciaRepo;

    @Autowired @Qualifier("mailSenderTenerife") private JavaMailSender mailSenderTF;
    @Autowired @Qualifier("mailSenderGranCanaria") private JavaMailSender mailSenderGC;

    public Page<EnvioEmail> listarPaginado(Integer centroId, Pageable pageable) {
        return emailRepo.findByCentroPaginado(centroId, pageable);
    }

    public List<EnvioEmail> listarTodo() {
        return emailRepo.findAll();
    }

    public List<EnvioEmail> listarPorDestinatario(String email) {
        return emailRepo.findByDestinatarioOrderByFechaHoraDtDesc(email);
    }

    @Transactional
    public void enviarEmailLibre(EmailRequestDTO dto) throws Exception {
        JavaMailSender sender = (dto.getIdCentro() == 1) ? mailSenderTF : mailSenderGC;
        String from = (dto.getIdCentro() == 1) ? "conserjeriaitc.tf@grupoenvera.org" : "conserjeriaitc.gc@grupoenvera.org";

        try {
            ejecutarEnvio(sender, from, dto.getDestinatario(), dto.getAsunto(), dto.getMensaje(), dto.getIdCentro());
            registrarLogEmail(dto, dto.getDestinatario());
            log.info(">>> EMAIL LIBRE enviado con éxito desde {}", from);
        } catch (Exception e) {
            log.error(">>> ERROR enviando email libre: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void enviarYRegistrarIncidencia(EmailRequestDTO dto) throws Exception {
        JavaMailSender sender;
        String from, to, asunto, toNombre;

        if (dto.getIdCentro() == 1) {
            sender = mailSenderTF;
            from = "conserjeriaitc.tf@grupoenvera.org";
            to = "cbetancor@itccanarias.org";
            toNombre = "María Carmen Betancor Reula";
            asunto = "Comunicación de Incidencia - Tenerife";
        } else {
            sender = mailSenderGC;
            from = "conserjeriaitc.gc@grupoenvera.org";
            to = "dgi_cebrian@itccanarias.org";
            toNombre = "Gestión de Infraestructuras Cebrián";
            asunto = "Comunicación de Incidencia - Las Palmas";
        }

        try {
            ejecutarEnvio(sender, from, to, asunto, dto.getMensaje(), dto.getIdCentro());

            Incidencia inc = new Incidencia();
            inc.setCentro(dto.getIdCentro());
            inc.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            inc.setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
            inc.setTexto(dto.getMensaje());
            inc.setComunicadoA(toNombre);
            inc.setEmailComunica(to);

            String emisor = dto.getDniEmisor();
            if (emisor != null && emisor.length() > 45) emisor = emisor.substring(0, 45);
            inc.setUsuario(emisor);

            inc.setFechaHoraDt(LocalDateTime.now());
            incidenciaRepo.save(inc);

            registrarLogEmail(dto, to);
        } catch (Exception e) {
            log.error(">>> ERROR en incidencia: {}", e.getMessage());
            throw e;
        }
    }

    private void ejecutarEnvio(JavaMailSender sender, String from, String to, String sub, String msg, Integer centro) throws Exception {
        MimeMessage mime = sender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(mime, "UTF-8");
        h.setFrom(from);
        h.setTo(to);
        h.setSubject(sub);
        h.setText(msg, true);

        // REPLICACIÓN EXACTA DE remisiva.php
        if (centro == 1) {
            h.addCc("adelaida.gomez@grupoenvera.org");
        } else {
            h.addCc("josea.henriquez@grupoenvera.org");
            h.addCc("conserjeriaitc.gc@grupoenvera.org");
        }

        // Copia oculta (BCC) que tenías en PHP
        h.addBcc("informaticaitc@jfgb.es");

        sender.send(mime);
    }

    private void registrarLogEmail(EmailRequestDTO dto, String dest) {
        EnvioEmail logEntidad = new EnvioEmail();
        logEntidad.setDestinatario(dest);
        logEntidad.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        logEntidad.setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        String texto = dto.getMensaje();
        if (texto != null && texto.length() > 1000) texto = texto.substring(0, 1000);
        logEntidad.setTexto(texto);
        String emisor = dto.getDniEmisor();
        if (emisor != null && emisor.length() > 45) emisor = emisor.substring(0, 45);
        logEntidad.setEmisor(emisor);
        logEntidad.setFechaHoraDt(LocalDateTime.now());
        emailRepo.save(logEntidad);
    }
}