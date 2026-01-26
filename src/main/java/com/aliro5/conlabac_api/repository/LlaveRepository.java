package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Integer> {

    List<Llave> findByIdCentro(Integer idCentro);

    Optional<Llave> findByCodigo(String codigo);

    @Query(value = "SELECT * FROM Llaves l " +
            "WHERE l.LlvCentro = :idCentro " +
            "AND l.LlvCodigo NOT IN (" +
            "    SELECT k.KeyLlvOrden FROM KeyMoves k " +
            "    WHERE k.KeyCentro = :idCentro AND (k.KeyFechaRecepcion IS NULL OR k.KeyFechaRecepcion = '')" +
            ")", nativeQuery = true)
    List<Llave> findDisponiblesPorCentro(@Param("idCentro") Integer idCentro);
}