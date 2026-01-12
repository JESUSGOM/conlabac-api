package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Telefono;
import com.aliro5.conlabac_api.repository.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {

    @Autowired
    private TelefonoRepository repo;

    public List<Telefono> listarHistorial(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaHoraRegistroDesc(idCentro);
    }

    public List<Telefono> listarPendientes(Integer idCentro) {
        return repo.findPendientes(idCentro);
    }

    // REGISTRAR NUEVA LLAMADA
    public Telefono registrar(Telefono tel) {
        LocalDateTime ahora = LocalDateTime.now();

        // Fechas de registro
        tel.setFechaHoraRegistro(ahora);
        tel.setFecha(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        tel.setHora(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

        // Estado inicial: Pendiente (0)
        tel.setComunicado(0);
        tel.setFechaEntrega(null);
        tel.setHoraEntrega(null);
        tel.setFechaHoraEntrega(null);

        return repo.save(tel);
    }

    // MARCAR COMO COMUNICADO
    public void marcarComunicado(Integer id) {
        Optional<Telefono> opcional = repo.findById(id);
        if (opcional.isPresent()) {
            Telefono tel = opcional.get();
            LocalDateTime ahora = LocalDateTime.now();

            tel.setComunicado(1); // 1 = Entregado
            tel.setFechaHoraEntrega(ahora);
            tel.setFechaEntrega(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            tel.setHoraEntrega(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

            repo.save(tel);
        }
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechasRegistro();
            repo.corregirFechasEntrega();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}