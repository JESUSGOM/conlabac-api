package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.KeyMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {

    // Buscar préstamos activos (donde la fecha de devolución es NULL o vacía)
    @Query(value = "SELECT * FROM KeyMoves " +
            "WHERE KeyCentro = :idCentro " +
            "AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findActivosPorCentro(Integer idCentro);

    // Buscar préstamos activos DE UNA FECHA ESPECÍFICA (Para el panel de hoy)
    @Query(value = "SELECT * FROM KeyMoves " +
            "WHERE KeyCentro = :idCentro " +
            "AND KeyFechaEntrega = :fechaHoy " +
            "AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findActivosHoyPorCentro(Integer idCentro, String fechaHoy);

    // --- SCRIPTS DE MANTENIMIENTO CON 'IGNORE' ---
    // Usamos UPDATE IGNORE para que si encuentra una fecha corrupta (ej: 2002-0-0),
    // no detenga el proceso y siga arreglando el resto.

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE KeyMoves " +
            "SET KeyFechaHoraEntrega_dt = STR_TO_DATE(CONCAT(KeyFechaEntrega, KeyHoraEntrega), '%Y%m%d%H%i%s') " +
            "WHERE KeyFechaHoraEntrega_dt IS NULL " +
            "  AND KeyFechaEntrega IS NOT NULL AND KeyFechaEntrega <> '' AND KeyFechaEntrega <> 'NULL' " +
            "  AND KeyHoraEntrega IS NOT NULL AND KeyHoraEntrega <> '' AND KeyHoraEntrega <> 'NULL'", nativeQuery = true)
    void corregirFechasEntrega();

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE KeyMoves " +
            "SET KeyFechaHoraRecepcion_dt = STR_TO_DATE(CONCAT(KeyFechaRecepcion, KeyHoraRecepcion), '%Y%m%d%H%i%s') " +
            "WHERE KeyFechaHoraRecepcion_dt IS NULL " +
            "  AND KeyFechaRecepcion IS NOT NULL AND KeyFechaRecepcion <> '' AND KeyFechaRecepcion <> 'NULL' " +
            "  AND KeyHoraRecepcion IS NOT NULL AND KeyHoraRecepcion <> '' AND KeyHoraRecepcion <> 'NULL'", nativeQuery = true)
    void corregirFechasRecepcion();
}