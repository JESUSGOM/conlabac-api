package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Incidencia guardar(Incidencia inc) {
        LocalDateTime ahora = LocalDateTime.now();

        // 1. ASIGNACIÓN AUTOMÁTICA DE FECHAS (Sincronizando Legacy y Moderno)
        inc.setFechaHora(ahora);
        inc.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //
        inc.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));     //

        // 2. LÓGICA DE NEGOCIO (Rellenar campos según centro)
        inc.setModoComunica("EMAIL");
        configurarDestinatario(inc);

        // 3. GUARDADO EN BASE DE DATOS
        Incidencia guardada = repo.save(inc);

        // 4. ENVÍO DE EMAIL (Ahora gestionado correctamente por Spring)
        // Ya no usamos "new Thread()", delegamos en el servicio asíncrono
        emailService.procesarIncidencia(guardada);

        return guardada;
    }

    private void configurarDestinatario(Incidencia inc) {
        // Centralizamos la lógica de asignación para que el método guardar sea legible
        if (inc.getIdCentro() != null) {
            if (inc.getIdCentro() == 1) {
                inc.setComunicadoA("María Carmen Betancor Reula");
                inc.setEmailComunica("cbetancor@itccanarias.org");
            } else if (inc.getIdCentro() == 2) {
                inc.setComunicadoA("Adriana Dominguez Sicilia");
                inc.setEmailComunica("adominguez@itccanarias.org");
            }
        }
    }

    public Optional<Incidencia> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}