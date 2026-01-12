package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Integer> {

    // Listar Histórico (Ordenado por registro descendente)
    List<Telefono> findByIdCentroOrderByFechaHoraRegistroDesc(Integer idCentro);

    // Listar Pendientes (Comunicado = 0 o NULL)
    @Query(value = "SELECT * FROM Telefonos WHERE TelCentro = :idCentro AND (TelComunicado = 0 OR TelComunicado IS NULL) ORDER BY TelFechaHoraRegistro_dt DESC", nativeQuery = true)
    List<Telefono> findPendientes(Integer idCentro);

    // --- MANTENIMIENTO ---
    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE Telefonos SET TelFechaHoraRegistro_dt = STR_TO_DATE(CONCAT(TelFecha, TelHora), '%Y%m%d%H%i%s') WHERE TelFechaHoraRegistro_dt IS NULL AND TelFecha <> '' AND TelHora <> ''", nativeQuery = true)
    void corregirFechasRegistro();

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE Telefonos SET TelFechaHoraEntrega_dt = STR_TO_DATE(CONCAT(TelFechaEntrega, TelHoraEntrega), '%Y%m%d%H%i%s') WHERE TelFechaHoraEntrega_dt IS NULL AND TelFechaEntrega <> '' AND TelHoraEntrega <> ''", nativeQuery = true)
    void corregirFechasEntrega();
}