package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantaRepository extends JpaRepository<Planta, Integer> {
    List<Planta> findByIdCentro(Integer idCentro);
}