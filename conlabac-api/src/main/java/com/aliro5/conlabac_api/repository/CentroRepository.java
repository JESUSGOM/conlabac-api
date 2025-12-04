package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Centro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroRepository extends JpaRepository<Centro, Integer> {
}