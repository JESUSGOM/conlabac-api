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
     * CORRECCIÓN: Se usa 'u.dni' y 'u.idCentro' para coincidir exactamente con los
     * atributos definidos en la clase Usuario.java.
     */
    @Query("SELECT e FROM EnvioEmail e WHERE e.emisor IN (SELECT u.dni FROM Usuario u WHERE u.idCentro = :centroId) ORDER BY e.fechaHoraDt DESC")
    Page<EnvioEmail> findByCentroPaginado(@Param("centroId") Integer centroId, Pageable pageable);

    Page<EnvioEmail> findAllByOrderByFechaHoraDtDesc(Pageable pageable);

    List<EnvioEmail> findByDestinatarioOrderByFechaHoraDtDesc(String destinatario);

    /**
     * Mantenimiento: Sincroniza el campo datetime uniendo Fecha y Hora.
     * Al ser nativeQuery = true, aquí se usan los nombres de las COLUMNAS reales de la tabla SQL.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE EnvioEmail SET EnEmFechaHora_dt = STR_TO_DATE(CONCAT(EnEmFecha, EnEmHora), '%Y%m%d%H%i%s') " +
            "WHERE EnEmFechaHora_dt IS NULL AND LENGTH(EnEmFecha) = 8 AND LENGTH(EnEmHora) = 6",
            nativeQuery = true)
    int actualizarFechasNulasEmails();
}