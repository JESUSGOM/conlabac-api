package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    // Listado simple para servicios antiguos
    List<Incidencia> findByCentroOrderByFechaHoraDtDesc(Integer centro);

    // Listado paginado para el nuevo panel web
    Page<Incidencia> findByCentroOrderByFechaHoraDtDesc(Integer centro, Pageable pageable);

    @Query("SELECT i FROM Incidencia i WHERE i.centro = :centro AND " +
            "SUBSTRING(i.fecha, 5, 2) = :mes AND SUBSTRING(i.fecha, 1, 4) = :anio")
    List<Incidencia> findByCentroMesAnio(@Param("centro") int centro, @Param("mes") String mes, @Param("anio") String anio);

    @Modifying
    @Transactional
    @Query("UPDATE Incidencia i SET i.fechaHoraDt = CURRENT_TIMESTAMP WHERE i.fechaHoraDt IS NULL")
    int actualizarFechasNulas();
}