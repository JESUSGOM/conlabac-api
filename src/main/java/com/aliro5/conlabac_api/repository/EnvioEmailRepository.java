package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.EnvioEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnvioEmailRepository extends JpaRepository<EnvioEmail, Integer> {
    // Buscar historial de envíos a un destinatario específico
    List<EnvioEmail> findByDestinatarioOrderByFechaHoraDtDesc(String destinatario);
}