package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Asegúrate de incluir esta importación

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Integer> {

    // Obtener todas las llaves de un centro
    List<Llave> findByIdCentro(Integer idCentro);

    // NUEVO: Método necesario para buscar una llave por su código (ej: K-101)
    Optional<Llave> findByCodigo(String codigo);

    // Consulta nativa para llaves disponibles
    @Query(value = "SELECT * FROM Llaves l " +
            "WHERE l.LlvCentro = :idCentro " +
            "AND l.LlvCodigo NOT IN (" +
            "    SELECT k.KeyLlvOrden FROM KeyMoves k " +
            "    WHERE k.KeyCentro = :idCentro AND k.KeyFechaRecepcion IS NULL" +
            ")", nativeQuery = true)
    List<Llave> findDisponiblesPorCentro(Integer idCentro);
}