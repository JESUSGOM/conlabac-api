package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    // Buscar movimientos por ID de centro (útil para filtros)
    List<Movimiento> findByIdCentro(Integer idCentro);

    // Busca: Mismo centro + Fecha Salida es NULL + Fecha Entrada entre Inicio y Fin del día
    List<Movimiento> findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(
            Integer idCentro,
            LocalDateTime inicioDia,
            LocalDateTime finDia
    );

    // ESTADÍSTICA: Cuenta visitas totales del mes
    @Query(value = "SELECT COUNT(*) FROM Movadoj WHERE MovCentro = :idCentro AND MONTH(MovFechaHoraEntrada_dt) = :mes AND YEAR(MovFechaHoraEntrada_dt) = :anio", nativeQuery = true)
    Integer contarVisitasMes(Integer idCentro, int mes, int anio);

    // ESTADÍSTICA: Agrupa por destino y cuenta
    @Query(value = "SELECT MovDestino, COUNT(*) as total FROM Movadoj " +
            "WHERE MovCentro = :idCentro AND MONTH(MovFechaHoraEntrada_dt) = :mes AND YEAR(MovFechaHoraEntrada_dt) = :anio " +
            "GROUP BY MovDestino ORDER BY total DESC", nativeQuery = true)
    List<Object[]> obtenerEstadisticasMes(Integer idCentro, int mes, int anio);

    // --- SCRIPTS DE MANTENIMIENTO (SQL Nativo) ---

    /**
     * MANTENIMIENTO A: De Texto antiguo a DateTime moderno (Entrada)
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Movadoj " +
            "SET MovFechaHoraEntrada_dt = STR_TO_DATE(CONCAT(MovFechaEntrada, MovHoraEntrada), '%Y%m%d%H%i%s') " +
            "WHERE MovFechaHoraEntrada_dt IS NULL " +
            "  AND MovFechaEntrada IS NOT NULL " +
            "  AND MovFechaEntrada <> '' " +
            "  AND MovFechaEntrada <> 'NULL' " +
            "  AND MovHoraEntrada IS NOT NULL " +
            "  AND MovHoraEntrada <> '' " +
            "  AND MovHoraEntrada <> 'NULL'", nativeQuery = true)
    int corregirFechasEntrada();

    /**
     * MANTENIMIENTO B: De Texto antiguo a DateTime moderno (Salida)
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Movadoj " +
            "SET MovFechaHoraSalida_dt = STR_TO_DATE(CONCAT(MovFechaSalida, MovHoraSalida), '%Y%m%d%H%i%s') " +
            "WHERE MovFechaHoraSalida_dt IS NULL " +
            "  AND MovFechaSalida IS NOT NULL " +
            "  AND MovFechaSalida <> '' " +
            "  AND MovFechaSalida <> 'NULL' " +
            "  AND MovHoraSalida IS NOT NULL " +
            "  AND MovHoraSalida <> '' " +
            "  AND MovHoraSalida <> 'NULL'", nativeQuery = true)
    int corregirFechasSalida();

    /**
     * MANTENIMIENTO C: De DateTime moderno a Texto antiguo (Entrada)
     * Rellena MovFechaEntrada (YYYYMMDD) y MovHoraEntrada (HHMMSS) si están vacíos
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Movadoj SET " +
            "MovFechaEntrada = DATE_FORMAT(MovFechaHoraEntrada_dt, '%Y%m%d'), " +
            "MovHoraEntrada = DATE_FORMAT(MovFechaHoraEntrada_dt, '%H%i%s') " +
            "WHERE MovFechaHoraEntrada_dt IS NOT NULL " +
            "AND (MovFechaEntrada IS NULL OR MovFechaEntrada = '' OR MovFechaEntrada = 'NULL')", nativeQuery = true)
    int corregirTextosDesdeDateTimeEntrada();

    /**
     * MANTENIMIENTO D: De DateTime moderno a Texto antiguo (Salida)
     * Rellena MovFechaSalida (YYYYMMDD) y MovHoraSalida (HHMMSS) si están vacíos
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Movadoj SET " +
            "MovFechaSalida = DATE_FORMAT(MovFechaHoraSalida_dt, '%Y%m%d'), " +
            "MovHoraSalida = DATE_FORMAT(MovFechaHoraSalida_dt, '%H%i%s') " +
            "WHERE MovFechaHoraSalida_dt IS NOT NULL " +
            "AND (MovFechaSalida IS NULL OR MovFechaSalida = '' OR MovFechaSalida = 'NULL')", nativeQuery = true)
    int corregirTextosDesdeDateTimeSalida();
}