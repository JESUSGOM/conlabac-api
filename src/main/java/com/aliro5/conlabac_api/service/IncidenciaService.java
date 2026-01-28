package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Loggers específicos para separar por sede
    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public Page<Incidencia> listarPaginado(Integer centroId, Pageable pageable) {
        return repo.findByCentroOrderByFechaHoraDtDesc(centroId, pageable);
    }

    public List<Incidencia> listarPorCentro(Integer idCentro) {
        return repo.findByCentroOrderByFechaHoraDtDesc(idCentro);
    }

    @Transactional
    public Incidencia guardar(Incidencia inc) {
        LocalDateTime ahora = LocalDateTime.now();

        inc.setFechaHoraDt(ahora);
        inc.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        inc.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

        inc.setModoComunica("EMAIL");
        configurarDestinatario(inc);

        Incidencia guardada = repo.save(inc);

        // Registro de LOG discriminado por centro
        registrarLogSede(inc);

        return guardada;
    }

    private void registrarLogSede(Incidencia inc) {
        String msg = "NUEVA INCIDENCIA registrada por " + inc.getUsuario() + ": " + inc.getTexto();
        if (inc.getCentro() != null && inc.getCentro() == 1) {
            logTF.info(msg);
        } else if (inc.getCentro() != null && inc.getCentro() == 2) {
            logGC.info(msg);
        }
    }

    private void configurarDestinatario(Incidencia inc) {
        if (inc.getCentro() != null) {
            if (inc.getCentro() == 1) {
                inc.setComunicadoA("María Carmen Betancor Reula");
                inc.setEmailComunica("cbetancor@itccanarias.org");
            } else if (inc.getCentro() == 2) {
                inc.setComunicadoA("Gestión de Infraestructuras Cebrián");
                inc.setEmailComunica("dgi_cebrian@itccanarias.org");
            }
        }
    }

    public Optional<Incidencia> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}