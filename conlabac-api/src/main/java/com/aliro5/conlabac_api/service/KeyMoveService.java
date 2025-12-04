package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.KeyMove;
import com.aliro5.conlabac_api.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    @Autowired
    private KeyMoveRepository repo;

    // 1. Listar llaves que están "en la calle"
    public List<KeyMove> listarPrestamosActivos(Integer idCentro) {
        return repo.findActivosPorCentro(idCentro);
    }

    // Listar prestamos de hoy (para el filtro del panel)
    public List<KeyMove> listarPrestamosActivosHoy(Integer idCentro) {
        String hoy = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return repo.findActivosHoyPorCentro(idCentro, hoy);
    }

    // 2. Registrar Entrega
    public KeyMove entregarLlave(KeyMove movimiento) {
        LocalDateTime ahora = LocalDateTime.now();
        movimiento.setFechaEntrega(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        movimiento.setHoraEntrega(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        movimiento.setFechaDevolucion(null);
        movimiento.setHoraDevolucion(null);
        return repo.save(movimiento);
    }

    // 3. Registrar Devolución
    public void devolverLlave(Integer idMovimiento) {
        Optional<KeyMove> opcional = repo.findById(idMovimiento);
        if (opcional.isPresent()) {
            KeyMove mov = opcional.get();
            LocalDateTime ahora = LocalDateTime.now();
            mov.setFechaDevolucion(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            mov.setHoraDevolucion(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            repo.save(mov);
        }
    }

    // --- ESTE ES EL MÉTODO QUE TE FALTABA ---
    public void ejecutarMantenimientoFechas() {
        try {
            // Llama a las consultas nativas del repositorio
            repo.corregirFechasEntrega();
            repo.corregirFechasRecepcion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}