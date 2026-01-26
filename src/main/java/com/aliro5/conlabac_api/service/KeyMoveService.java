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
     * Devuelve el número de llaves que están fuera actualmente.
     * Útil para mostrar alertas o contadores en el Dashboard.
     */
    public long contarPendientes(Integer idCentro) {
        List<KeyMove> lista = repo.findByCentroAndFechaDevolucionIsNull(idCentro);
        return (lista != null) ? lista.size() : 0;
    }

    /**
     * Lista los movimientos realizados HOY (Entregas de hoy)
     */
    public List<KeyMove> listarPrestadasHoy(Integer idCentro) {
        String hoyStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return repo.findActivosHoyPorCentro(idCentro, hoyStr);
    }

    /**
     * Lista las llaves que han sido entregadas y aún NO han sido devueltas
     */
    public List<KeyMove> listarPendientesDeDevolucion(Integer idCentro) {
        return repo.findByCentroAndFechaDevolucionIsNull(idCentro);
    }

    public Optional<KeyMove> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public KeyMove entregarLlave(KeyMove movimiento) {
        LocalDateTime ahora = LocalDateTime.now();

        // 1. Llenamos los campos de texto (Legacy/Compatibilidad)
        movimiento.setFechaEntrega(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        movimiento.setHoraEntrega(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

        // 2. Sincronizamos el campo DateTime real para la BD (_dt)
        movimiento.setKeyFechaHoraEntregaDt(ahora);

        // 3. Limpiamos datos de devolución por seguridad
        movimiento.setFechaDevolucion(null);
        movimiento.setHoraDevolucion(null);
        movimiento.setKeyFechaHoraRecepcionDt(null);

        return repo.save(movimiento);
    }

    @Transactional
    public void devolverLlave(Integer idMovimiento) {
        repo.findById(idMovimiento).ifPresent(mov -> {
            LocalDateTime ahora = LocalDateTime.now();

            // 1. Llenamos los campos de texto de devolución
            mov.setFechaDevolucion(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            mov.setHoraDevolucion(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));

            // 2. Sincronizamos el campo DateTime de recepción (_dt)
            mov.setKeyFechaHoraRecepcionDt(ahora);

            repo.save(mov);
        });
    }

    /**
     * Ejecuta la corrección masiva de registros que tengan los campos _dt vacíos.
     */
    @Transactional
    public void ejecutarMantenimientoFechas() {
        try {
            System.out.println("Iniciando mantenimiento de sincronización de fechas...");
            // Llamada a las queries nativas del KeyMoveRepository
            repo.corregirFechasEntrega();
            repo.corregirFechasRecepcion();
            System.out.println("Mantenimiento finalizado con éxito.");
        } catch (Exception e) {
            System.err.println("API Error Mantenimiento Llaves: " + e.getMessage());
            throw e; // Lanzamos para que Transactional pueda hacer rollback si es necesario
        }
    }
}