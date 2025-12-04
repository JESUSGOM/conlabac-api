package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Proveedor;
import com.aliro5.conlabac_api.model.ProveedorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, ProveedorId> {
    // Listar proveedores de un centro
    List<Proveedor> findByIdCentro(Integer idCentro);
}