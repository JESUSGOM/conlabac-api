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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Service
public class EnvioEmailService {

    @Autowired private EnvioEmailRepository emailRepo;
    @Autowired private IncidenciaRepository incidenciaRepo;

    // Loggers para discriminación de sedes en el archivo de logs
    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    // Método para la vista paginada (usado en las tablas principales)
    public Page<EnvioEmail> listarPaginado(Integer centroId, Pageable pageable) {
        return emailRepo.findByCentroPaginado(centroId, pageable);
    }

    // --- CORRECCIÓN: Métodos restaurados para que EnvioEmailRestController compile ---

    public List<EnvioEmail> listarTodo() {
        return emailRepo.findAll();
    }

    public List<EnvioEmail> listarPorDestinatario(String email) {
        return emailRepo.findByDestinatarioOrderByFechaHoraDtDesc(email);
    }

    // --------------------------------------------------------------------------------

    public void enviarEmailLibre(EmailRequestDTO dto) throws Exception {
        JavaMailSenderImpl mailSender = configurarMailSender(dto.getIdCentro());
        enviarMimeMessage(mailSender, dto.getDestinatario(), null, dto.getAsunto(), dto.getMensaje(), dto.getIdCentro());
        registrarEnEnvioEmail(dto, dto.getDestinatario());

        String msg = "EMAIL LIBRE enviado a " + dto.getDestinatario() + " - Asunto: " + dto.getAsunto();
        if (dto.getIdCentro() == 1) logTF.info(msg);
        else logGC.info(msg);
    }

    public void enviarYRegistrarIncidencia(EmailRequestDTO dto) throws Exception {
        String toEmail, toNombre, asunto, pass, from;
        if (dto.getIdCentro() == 1) {
            from = "conserjeriaitc.tf@grupoenvera.org"; pass = "envara.2026";
            toEmail = "cbetancor@itccanarias.org"; toNombre = "María Carmen Betancor Reula";
            asunto = "Comunicación de Incidencia desde Tenerife.";
        } else {
            from = "conserjeriaitc.gc@grupoenvera.org"; pass = "Envera2026";
            toEmail = "dgi_cebrian@itccanarias.org"; toNombre = "Gestión de Infraestructuras Cebrián";
            asunto = "Comunicación de Incidencia desde Las Palmas.";
        }

        JavaMailSenderImpl mailSender = configurarMailSender(dto.getIdCentro());
        mailSender.setUsername(from); mailSender.setPassword(pass);
        enviarMimeMessage(mailSender, toEmail, toNombre, asunto, dto.getMensaje(), dto.getIdCentro());

        // Guardar la incidencia en la base de datos
        Incidencia inc = new Incidencia();
        inc.setCentro(dto.getIdCentro());
        inc.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        inc.setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        inc.setTexto(dto.getMensaje());
        inc.setComunicadoA(toNombre);
        inc.setEmailComunica(toEmail);
        inc.setUsuario(dto.getDniEmisor());
        inc.setFechaHoraDt(LocalDateTime.now());
        incidenciaRepo.save(inc);

        registrarEnEnvioEmail(dto, toEmail);

        // Registro en el LOG físico de la sede
        String logMsg = "EMAIL INCIDENCIA enviado a " + toEmail + " por " + dto.getDniEmisor();
        if (dto.getIdCentro() == 1) logTF.info(logMsg);
        else logGC.info(logMsg);
    }

    private JavaMailSenderImpl configurarMailSender(Integer idCentro) {
        JavaMailSenderImpl ms = new JavaMailSenderImpl();
        ms.setHost("smtp.office365.com"); ms.setPort(587);
        if (idCentro == 1) {
            ms.setUsername("conserjeriaitc.tf@grupoenvera.org");
            ms.setPassword("envara.2026");
        } else {
            ms.setUsername("conserjeriaitc.gc@grupoenvera.org");
            ms.setPassword("Envera2026");
        }
        configurarProperties(ms);
        return ms;
    }

    private void enviarMimeMessage(JavaMailSenderImpl ms, String to, String name, String sub, String msg, Integer centro) throws Exception {
        MimeMessage mime = ms.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(mime, "UTF-8");
        h.setFrom(ms.getUsername()); h.setTo(to); h.setSubject(sub); h.setText(msg);
        if (centro == 1) h.addCc("gloria.santana@grupoenvera.org");
        else h.addCc("josea.henriquez@grupoenvera.org");
        h.addBcc("informaticaitc@jfgb.es");
        ms.send(mime);
    }

    private void configurarProperties(JavaMailSenderImpl ms) {
        Properties p = ms.getJavaMailProperties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.starttls.required", "true");
    }

    private void registrarEnEnvioEmail(EmailRequestDTO dto, String dest) {
        EnvioEmail log = new EnvioEmail();
        log.setDestinatario(dest);
        log.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        log.setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        log.setTexto(dto.getMensaje());
        log.setEmisor(dto.getDniEmisor());
        log.setFechaHoraDt(LocalDateTime.now());
        emailRepo.save(log);
    }
}