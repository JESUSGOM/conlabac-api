package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Garaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GarajeRepository extends JpaRepository<Garaje, Integer> {

    /**
     * Devuelve solo los vehículos que están actualmente en el garaje.
     * (Aquellos cuya fecha de salida es NULL)
     */
    List<Garaje> findByFechaSalidaIsNullOrderByFechaEntradaDesc();

    /**
     * Listar todos los registros (histórico completo).
     */
    List<Garaje> findAllByOrderByFechaEntradaDesc();

    // --- MANTENIMIENTO AUTOMÁTICO DE FECHAS ---

    /**
     * Sincroniza la columna de texto antigua 'GrjFecha' con la nueva columna DATETIME 'GrjFecha_dt'.
     * Esto es vital para que los registros antiguos de tu sistema PHP aparezcan en la nueva web.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE Garaje " +
            "SET GrjFecha_dt = STR_TO_DATE(GrjFecha, '%Y%m%d') " +
            "WHERE GrjFecha_dt IS NULL " +
            "  AND GrjFecha IS NOT NULL " +
            "  AND GrjFecha <> '' " +
            "  AND GrjFecha <> 'NULL' " +
            "  AND LENGTH(GrjFecha) = 8", nativeQuery = true)
    void corregirFechas();
}