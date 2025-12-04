package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    // Buscar por centro y ordenar descendente (la más nueva arriba)
    // Usamos el campo moderno fechaHora para ordenar con precisión
    List<Incidencia> findByIdCentroOrderByFechaHoraDesc(Integer idCentro);

    @Query(value = "SELECT * FROM Incidencias WHERE MONTH(IncFechaHora_dt) = :mes AND YEAR(IncFechaHora_dt) = :anio AND IncCentro = :idCentro ORDER BY IncFechaHora_dt", nativeQuery = true)
    List<Incidencia> findByMesAndAnioAndCentro(int mes, int anio, int idCentro);

    @Query(value = "SELECT * FROM Incidencias WHERE IncCentro = :idCentro AND MONTH(IncFechaHora_dt) = :mes AND YEAR(IncFechaHora_dt) = :anio ORDER BY IncFechaHora_dt ASC", nativeQuery = true)
    List<Incidencia> findByCentroMesAnio(Integer idCentro, int mes, int anio);
}