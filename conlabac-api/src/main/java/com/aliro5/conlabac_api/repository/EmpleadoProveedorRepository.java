package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoProveedorRepository extends JpaRepository<EmpleadoProveedor, Integer> {
    // Listar empleados de una empresa concreta en un centro concreto
    List<EmpleadoProveedor> findByCifProveedorAndIdCentro(String cifProveedor, Integer idCentro);

    // Buscar TODOS los empleados de un centro (sin importar empresa) ---
    List<EmpleadoProveedor> findByIdCentro(Integer idCentro);
}