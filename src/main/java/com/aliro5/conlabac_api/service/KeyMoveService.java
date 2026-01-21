package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.KeyMove;
import com.aliro5.conlabac_api.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    @Autowired
    private KeyMoveRepository repo;

    /**
     * Lista los movimientos realizados HOY (Entregas de hoy)
     */
    public List<KeyMove> listarPrestadasHoy(Integer idCentro) {
        String hoyStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return repo.findActivosHoyPorCentro(idCentro, hoyStr);
    }

    /**
     * NUEVO: Lista las llaves que han sido entregadas y aún NO han sido devueltas
     * (Independientemente de si fue hoy o ayer)
     */
    public List<KeyMove> listarPendientesDeDevolucion(Integer idCentro) {
        // Asumiendo que en tu repositorio tienes un método que busque donde FechaDevolucion es NULL
        return repo.findByCentroAndFechaDevolucionIsNull(idCentro);
    }

    public Optional<KeyMove> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public KeyMove entregarLlave(KeyMove movimiento) {
        LocalDateTime ahora = LocalDateTime.now();

        // Sincronizamos campos de texto (compatibilidad legacy) y campos LocalDateTime
        movimiento.setFechaEntrega(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        movimiento.setHoraEntrega(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        movimiento.setKeyFechaHoraEntregaDt(ahora);

        // Limpiamos cualquier dato previo de devolución por seguridad
        movimiento.setFechaDevolucion(null);
        movimiento.setHoraDevolucion(null);
        movimiento.setKeyFechaHoraRecepcionDt(null);

        return repo.save(movimiento);
    }

    @Transactional
    public void devolverLlave(Integer idMovimiento) {
        repo.findById(idMovimiento).ifPresent(mov -> {
            LocalDateTime ahora = LocalDateTime.now();

            // Registramos la vuelta de la llave
            mov.setFechaDevolucion(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            mov.setHoraDevolucion(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            mov.setKeyFechaHoraRecepcionDt(ahora);

            repo.save(mov);
        });
    }

    @Transactional
    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechasEntrega();
            repo.corregirFechasRecepcion();
        } catch (Exception e) {
            System.err.println("API Error Mantenimiento Llaves: " + e.getMessage());
        }
    }
}