package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.AperturaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AperturaExtraRepository extends JpaRepository<AperturaExtra, Integer> {
    // Listar por centro, ordenado por fecha descendente (más reciente primero)
    List<AperturaExtra> findByIdCentroOrderByFechaDesc(Integer idCentro);

    @Query("SELECT a FROM AperturaExtra a WHERE MONTH(a.fecha) = :mes AND YEAR(a.fecha) = :anio AND a.idCentro = :idCentro ORDER BY a.fecha, a.horaInicio")
    List<AperturaExtra> findByMesAndAnioAndCentro(int mes, int anio, int idCentro);

    // ...
    @Query("SELECT a FROM AperturaExtra a WHERE a.idCentro = :idCentro AND MONTH(a.fecha) = :mes AND YEAR(a.fecha) = :anio ORDER BY a.fecha, a.horaInicio")
    List<AperturaExtra> findByCentroMesAnio(Integer idCentro, int mes, int anio);
}