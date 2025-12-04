package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Integer> {

    // Método para obtener todas las llaves de un centro específico
    // Spring Data JPA genera la consulta SQL automáticamente basándose en el nombre del método
    List<Llave> findByIdCentro(Integer idCentro);

    // Selecciona las llaves del centro X cuyo CÓDIGO NO ESTÉ en la tabla de movimientos activos
    @Query(value = "SELECT * FROM Llaves l " +
            "WHERE l.LlvCentro = :idCentro " +
            "AND l.LlvCodigo NOT IN (" +
            "    SELECT k.KeyLlvOrden FROM KeyMoves k " +
            "    WHERE k.KeyCentro = :idCentro AND k.KeyFechaRecepcion IS NULL" +
            ")", nativeQuery = true)
    List<Llave> findDisponiblesPorCentro(Integer idCentro);
}