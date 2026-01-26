package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.KeyMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {

    @Query(value = "SELECT * FROM KeyMoves WHERE KeyCentro = :idCentro AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findByCentroAndFechaDevolucionIsNull(@Param("idCentro") Integer idCentro);

    @Query(value = "SELECT * FROM KeyMoves WHERE KeyCentro = :idCentro AND KeyFechaEntrega = :fechaHoy AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findActivosHoyPorCentro(@Param("idCentro") Integer idCentro, @Param("fechaHoy") String fechaHoy);

    Optional<KeyMove> findById(Integer id);

    // =========================================================================
    // MANTENIMIENTO DE FECHAS (Actualización de campos _dt)
    // =========================================================================

    @Modifying
    @Transactional
    @Query(value = "UPDATE KeyMoves SET KeyFechaHoraEntrega_dt = STR_TO_DATE(CONCAT(KeyFechaEntrega, KeyHoraEntrega), '%Y%m%d%H%i%s') " +
            "WHERE (KeyFechaHoraEntrega_dt IS NULL) " +
            "AND KeyFechaEntrega REGEXP '^[0-9]{8}$' AND KeyHoraEntrega REGEXP '^[0-9]{6}$'", nativeQuery = true)
    void corregirFechasEntrega();

    @Modifying
    @Transactional
    @Query(value = "UPDATE KeyMoves SET KeyFechaHoraRecepcion_dt = STR_TO_DATE(CONCAT(KeyFechaRecepcion, KeyHoraRecepcion), '%Y%m%d%H%i%s') " +
            "WHERE (KeyFechaHoraRecepcion_dt IS NULL) " +
            "AND KeyFechaRecepcion REGEXP '^[0-9]{8}$' AND KeyHoraRecepcion REGEXP '^[0-9]{6}$'", nativeQuery = true)
    void corregirFechasRecepcion();
}