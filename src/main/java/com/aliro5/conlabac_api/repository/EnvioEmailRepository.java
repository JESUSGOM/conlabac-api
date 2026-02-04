package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.EnvioEmail;
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
public interface EnvioEmailRepository extends JpaRepository<EnvioEmail, Integer> {

    /**
     * Filtra los emails por el centro al que pertenece el emisor.
     * Esta consulta permite que en el panel web cada centro vea solo su historial.
     */
    @Query("SELECT e FROM EnvioEmail e WHERE e.emisor IN " +
            "(SELECT u.dni FROM Usuario u WHERE u.idCentro = :centroId) " +
            "ORDER BY e.fechaHoraDt DESC")
    Page<EnvioEmail> findByCentroPaginado(@Param("centroId") Integer centroId, Pageable pageable);

    /**
     * Listado general ordenado por fecha descendente.
     */
    Page<EnvioEmail> findAllByOrderByFechaHoraDtDesc(Pageable pageable);

    /**
     * Busca envíos realizados a un destinatario específico.
     */
    List<EnvioEmail> findByDestinatarioOrderByFechaHoraDtDesc(String destinatario);

    /**
     * Mantenimiento automático:
     * Une las columnas de texto EnEmFecha y EnEmHora en el campo LocalDateTime EnEmFechaHora_dt.
     * Se usa Native Query para aprovechar la función STR_TO_DATE de MySQL/MariaDB.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE EnvioEmail SET EnEmFechaHora_dt = STR_TO_DATE(CONCAT(EnEmFecha, EnEmHora), '%Y%m%d%H%i%s') " +
            "WHERE (EnEmFechaHora_dt IS NULL OR EnEmFechaHora_dt = '') " +
            "AND EnEmFecha IS NOT NULL AND EnEmFecha <> '' AND EnEmFecha <> 'NULL' " +
            "AND EnEmHora IS NOT NULL AND EnEmHora <> '' AND EnEmHora <> 'NULL' " +
            "AND LENGTH(EnEmFecha) = 8 AND LENGTH(EnEmHora) = 6",
            nativeQuery = true)
    int actualizarFechasNulasEmails();
}