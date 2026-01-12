package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.EntreTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EntreTurnoRepository extends JpaRepository<EntreTurno, Integer> {

    // Listados ordenados por fecha descendente (Lo más nuevo arriba)
    List<EntreTurno> findByIdCentroOrderByFechaHoraEscritoDesc(Integer idCentro);

    List<EntreTurno> findByIdCentroAndFechaHoraLeidoIsNullOrderByFechaHoraEscritoDesc(Integer idCentro);

    // --- MANTENIMIENTO AUTOMÁTICO ---

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE EntreTurnos " +
            "SET EntFechaHoraEscrito_dt = STR_TO_DATE(CONCAT(EntFescrito, EntHescrito), '%Y%m%d%H%i%s') " +
            "WHERE EntFechaHoraEscrito_dt IS NULL " +
            "  AND EntFescrito IS NOT NULL AND EntFescrito <> '' AND EntFescrito <> 'NULL' " +
            "  AND EntHescrito IS NOT NULL AND EntHescrito <> '' AND EntHescrito <> 'NULL'", nativeQuery = true)
    void corregirFechasEscritura();

    @Modifying
    @Transactional
    @Query(value = "UPDATE IGNORE EntreTurnos " +
            "SET EntFechaHoraLeido_dt = STR_TO_DATE(CONCAT(EntFleido, EntHleido), '%Y%m%d%H%i%s') " +
            "WHERE EntFechaHoraLeido_dt IS NULL " +
            "  AND EntFleido IS NOT NULL AND EntFleido <> '' AND EntFleido <> 'NULL' " +
            "  AND EntHleido IS NOT NULL AND EntHleido <> '' AND EntHleido <> 'NULL'", nativeQuery = true)
    void corregirFechasLectura();
}