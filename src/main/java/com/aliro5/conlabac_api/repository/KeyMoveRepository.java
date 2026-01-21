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

    /**
     * Busca todos los movimientos que no han sido devueltos aún.
     * Usado por el Service: listarPendientesDeDevolucion
     */
    @Query(value = "SELECT * FROM KeyMoves WHERE KeyCentro = :idCentro AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findByCentroAndFechaDevolucionIsNull(@Param("idCentro") Integer idCentro);

    /**
     * Alias para compatibilidad con métodos antiguos si existen
     */
    @Query(value = "SELECT * FROM KeyMoves WHERE KeyCentro = :idCentro AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findActivosPorCentro(@Param("idCentro") Integer idCentro);

    /**
     * Busca las entregas realizadas hoy que siguen activas.
     */
    @Query(value = "SELECT * FROM KeyMoves WHERE KeyCentro = :idCentro AND KeyFechaEntrega = :fechaHoy AND (KeyFechaRecepcion IS NULL OR KeyFechaRecepcion = '')", nativeQuery = true)
    List<KeyMove> findActivosHoyPorCentro(@Param("idCentro") Integer idCentro, @Param("fechaHoy") String fechaHoy);

    // Método estándar de JPA para buscar por ID
    Optional<KeyMove> findById(Integer id);

    // =========================================================================
    // MANTENIMIENTO DE FECHAS (Consultas Nativa para compatibilidad con la BD antigua)
    // =========================================================================

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE KeyMoves SET KeyFechaHoraEntrega_dt = STR_TO_DATE(CONCAT(KeyFechaEntrega, KeyHoraEntrega), '%Y%m%d%H%i%s') WHERE KeyFechaHoraEntrega_dt IS NULL AND KeyFechaEntrega IS NOT NULL AND KeyFechaEntrega <> '' AND KeyFechaEntrega <> 'NULL' AND KeyHoraEntrega IS NOT NULL AND KeyHoraEntrega <> '' AND KeyHoraEntrega <> 'NULL'", nativeQuery = true)
    void corregirFechasEntrega();

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE KeyMoves SET KeyFechaHoraRecepcion_dt = STR_TO_DATE(CONCAT(KeyFechaRecepcion, KeyHoraRecepcion), '%Y%m%d%H%i%s') WHERE KeyFechaHoraRecepcion_dt IS NULL AND KeyFechaRecepcion IS NOT NULL AND KeyFechaRecepcion <> '' AND KeyFechaRecepcion <> 'NULL' AND KeyHoraRecepcion IS NOT NULL AND KeyHoraRecepcion <> '' AND KeyHoraRecepcion <> 'NULL'", nativeQuery = true)
    void corregirFechasRecepcion();
}