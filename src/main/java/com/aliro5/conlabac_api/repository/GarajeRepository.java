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

    // Listar ordenado por fecha (de más reciente a más antigua)
    List<Garaje> findAllByOrderByFechaDesc();

    // --- MANTENIMIENTO AUTOMÁTICO DE FECHAS ---
    // Convierte el varchar '20250101' a una fecha real en la columna _dt
    // Usamos UPDATE IGNORE para saltar datos corruptos si los hubiera
    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE Garaje " +
            "SET GrjFecha_dt = STR_TO_DATE(GrjFecha, '%Y%m%d') " +
            "WHERE GrjFecha_dt IS NULL " +
            "  AND GrjFecha IS NOT NULL " +
            "  AND GrjFecha <> '' " +
            "  AND GrjFecha <> 'NULL'", nativeQuery = true)
    void corregirFechas();
}