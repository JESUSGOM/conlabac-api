package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Paquete;
import com.aliro5.conlabac_api.repository.PaqueteRepository;
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

    public List<Paquete> listarPendientes(Integer idCentro) {
        return repo.findByIdCentroAndComunicadoOrderByFechaHoraRecepcionDesc(idCentro, "NO");
    }

    public List<Paquete> listarTodos(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaHoraRecepcionDesc(idCentro);
    }

    // NUEVO: Método para buscar por ID usado en tests
    public Optional<Paquete> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public Paquete recibirPaquete(Paquete pkt) {
        LocalDateTime ahora = LocalDateTime.now();
        pkt.setFechaHoraRecepcion(ahora);
        pkt.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        pkt.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        pkt.setComunicado("NO");
        return repo.save(pkt);
    }

    public void marcarComoEntregado(Integer id) {
        repo.findById(id).ifPresent(pkt -> {
            pkt.setComunicado("SI");
            repo.save(pkt);
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