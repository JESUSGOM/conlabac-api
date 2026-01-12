package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    // Listar paquetes pendientes de comunicar/entregar en un centro
    List<Paquete> findByIdCentroAndComunicadoOrderByFechaHoraRecepcionDesc(Integer idCentro, String comunicado);

    // Listar todos los paquetes de un centro (Histórico)
    List<Paquete> findByIdCentroOrderByFechaHoraRecepcionDesc(Integer idCentro);

    // MANTENIMIENTO: Script para arreglar fechas antiguas (igual que los otros módulos)
    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE Paqueteria " +
            "SET PktFechaHoraRecepcion_dt = STR_TO_DATE(CONCAT(PktFecha, PktHora), '%Y%m%d%H%i%s') " +
            "WHERE PktFechaHoraRecepcion_dt IS NULL " +
            "  AND PktFecha IS NOT NULL AND PktFecha <> '' AND PktFecha <> 'NULL' " +
            "  AND PktHora IS NOT NULL AND PktHora <> '' AND PktHora <> 'NULL'", nativeQuery = true)
    void corregirFechas();
}