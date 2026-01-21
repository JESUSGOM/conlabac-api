package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    /**
     * MÉTODO DE MANTENIMIENTO CORREGIDO:
     * Actualiza el campo LocalDateTime (IncFechaHora_dt) solo si los campos legacy
     * tienen el formato correcto (8 caracteres para fecha y 6 para hora) y son numéricos.
     * Esto evita errores como "Incorrect datetime value: '00'".
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Incidencias " +
            "SET IncFechaHora_dt = STR_TO_DATE(CONCAT(IncFecha, IncHora), '%Y%m%d%H%i%s') " +
            "WHERE IncFechaHora_dt IS NULL " +
            "AND LENGTH(IncFecha) = 8 " +
            "AND LENGTH(IncHora) = 6 " +
            "AND IncFecha REGEXP '^[0-9]+$' " +
            "AND IncHora REGEXP '^[0-9]+$'", nativeQuery = true)
    int actualizarFechasNulas();

    // Buscar por centro y ordenar descendente (la más nueva arriba)
    List<Incidencia> findByIdCentroOrderByFechaHoraDesc(Integer idCentro);

    @Query(value = "SELECT * FROM Incidencias WHERE MONTH(IncFechaHora_dt) = :mes AND YEAR(IncFechaHora_dt) = :anio AND IncCentro = :idCentro ORDER BY IncFechaHora_dt", nativeQuery = true)
    List<Incidencia> findByMesAndAnioAndCentro(@Param("mes") int mes, @Param("anio") int anio, @Param("idCentro") int idCentro);

    @Query(value = "SELECT * FROM Incidencias WHERE IncCentro = :idCentro AND MONTH(IncFechaHora_dt) = :mes AND YEAR(IncFechaHora_dt) = :anio ORDER BY IncFechaHora_dt ASC", nativeQuery = true)
    List<Incidencia> findByCentroMesAnio(@Param("idCentro") Integer idCentro, @Param("mes") int mes, @Param("anio") int anio);
}