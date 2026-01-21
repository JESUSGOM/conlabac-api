package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importación necesaria para evitar errores de tipo

@Repository
public interface EmpleadoProveedorRepository extends JpaRepository<EmpleadoProveedor, Integer> {

    // 1. Listar empleados de una empresa concreta en un centro concreto
    // Usado por EmpleadoProveedorServiceTest.testListarPorCifYCentro
    List<EmpleadoProveedor> findByCifProveedorAndIdCentro(String cifProveedor, Integer idCentro);

    // 2. Buscar TODOS los empleados de un centro
    List<EmpleadoProveedor> findByIdCentro(Integer idCentro);

    // 3. NUEVO: Buscar un empleado específico por su NIF personal
    // Muy útil para validaciones de seguridad en el acceso
    Optional<EmpleadoProveedor> findByNif(String nif);

    // 4. NUEVO: Buscar empleados por cargo dentro de un centro
    List<EmpleadoProveedor> findByIdCentroAndCargoContaining(Integer idCentro, String cargo);
}