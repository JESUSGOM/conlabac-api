package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repo;

    @Autowired
    private EmailService emailService;

    public List<Incidencia> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaHoraDesc(idCentro);
    }

    public Incidencia guardar(Incidencia inc) {
        LocalDateTime ahora = LocalDateTime.now();

        // Asignar fechas
        inc.setFechaHora(ahora);
        inc.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        inc.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

        // Replicamos lógica PHP: Rellenar campos automáticos
        inc.setModoComunica("EMAIL");
        // El campo 'IncComunicadoA' se rellenaba en PHP con el nombre del destinatario fijo
        if (inc.getIdCentro() == 1) inc.setComunicadoA("María Carmen Betancor Reula");
        if (inc.getIdCentro() == 2) inc.setComunicadoA("Adriana Dominguez Sicilia");
        if (inc.getIdCentro() == 1) inc.setEmailComunica("cbetancor@itccanarias.org");
        if (inc.getIdCentro() == 2) inc.setEmailComunica("adominguez@itccanarias.org");

        // 1. Guardar Incidencia en BD
        Incidencia guardada = repo.save(inc);

        // 2. Procesar Envío de Email y Log (En segundo plano)
        new Thread(() -> {
            emailService.procesarIncidencia(guardada);
        }).start();

        return guardada;
    }

    public Optional<Incidencia> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}