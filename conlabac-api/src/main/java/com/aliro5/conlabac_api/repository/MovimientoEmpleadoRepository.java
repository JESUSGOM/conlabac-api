package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoEmpleadoRepository extends JpaRepository<MovimientoEmpleado, Integer> {

    // Listar todos los trabajadores que están DENTRO del centro (Salida es nula)
    // Ordenamos por entrada descendente (los últimos en llegar primero)
    List<MovimientoEmpleado> findByIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(Integer idCentro);

    // Buscar si un empleado específico ya está dentro (para evitar duplicados al fichar entrada)
    // Buscamos el último registro de ese NIF en ese Centro que no tenga salida
    Optional<MovimientoEmpleado> findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(String nifEmpleado, Integer idCentro);
}