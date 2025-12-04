package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    // Listar contactos de un centro ordenados por nombre
    List<Contacto> findByIdCentroOrderByNombreAsc(Integer idCentro);
}