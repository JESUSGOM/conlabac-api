package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    List<Alquiler> findByIdCentro(Integer idCentro);
}