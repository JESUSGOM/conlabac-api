package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Paquete;
import com.aliro5.conlabac_api.repository.PaqueteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService {

    @Autowired
    private PaqueteRepository repo;

    // Loggers para discriminación de sedes (Configurados en logback-spring.xml)
    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public List<Paquete> listarPendientes(Integer idCentro) {
        return repo.findByIdCentroAndComunicadoOrderByFechaHoraRecepcionDesc(idCentro, "NO");
    }

    public List<Paquete> listarTodos(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaHoraRecepcionDesc(idCentro);
    }

    public Optional<Paquete> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public Paquete recibirPaquete(Paquete pkt) {
        LocalDateTime ahora = LocalDateTime.now();
        pkt.setFechaHoraRecepcion(ahora);
        pkt.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        pkt.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        pkt.setComunicado("NO");

        Paquete guardado = repo.save(pkt);

        // CORRECCIÓN: Ahora usamos getEmisor() y getDestinatario() que existen en Paquete.java
        String msg = "PAQUETE RECIBIDO - De: " + pkt.getEmisor() + " Para: " + pkt.getDestinatario() +
                " - Mensajería: " + pkt.getMensajeria() + " (" + pkt.getBultos() + " bultos)";

        // Registro en el log de la sede correspondiente
        if (pkt.getIdCentro() != null) {
            if (pkt.getIdCentro() == 1) logTF.info(msg);
            else if (pkt.getIdCentro() == 2) logGC.info(msg);
        }

        return guardado;
    }

    public void marcarComoEntregado(Integer id) {
        repo.findById(id).ifPresent(pkt -> {
            pkt.setComunicado("SI");
            repo.save(pkt);

            // LOG DE SEDE
            String msg = "PAQUETE ENTREGADO - ID: " + id + " - Destinatario: " + pkt.getDestinatario();
            if (pkt.getIdCentro() != null) {
                if (pkt.getIdCentro() == 1) logTF.info(msg);
                else if (pkt.getIdCentro() == 2) logGC.info(msg);
            }
        });
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}