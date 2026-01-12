package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.aliro5.conlabac_api.model.Proveedor;
import com.aliro5.conlabac_api.model.ProveedorId;
import com.aliro5.conlabac_api.repository.EmpleadoProveedorRepository;
import com.aliro5.conlabac_api.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private EmpleadoProveedorRepository empleadoRepo;

    // --- PROVEEDORES ---
    public List<Proveedor> listarProveedores(Integer idCentro) {
        return proveedorRepo.findByIdCentro(idCentro);
    }

    public Proveedor guardarProveedor(Proveedor p) {
        if (p.getFechaAlta() == null) p.setFechaAlta(LocalDate.now());
        return proveedorRepo.save(p);
    }

    public Optional<Proveedor> obtenerProveedor(String cif, Integer idCentro) {
        return proveedorRepo.findById(new ProveedorId(cif, idCentro));
    }

    // --- EMPLEADOS ---
    public List<EmpleadoProveedor> listarEmpleados(String cif, Integer idCentro) {
        return empleadoRepo.findByCifProveedorAndIdCentro(cif, idCentro);
    }

    public EmpleadoProveedor guardarEmpleado(EmpleadoProveedor emp) {
        if (emp.getFechaAcceso() == null) emp.setFechaAcceso(LocalDate.now());
        return empleadoRepo.save(emp);
    }

    public void eliminarEmpleado(Integer id) {
        empleadoRepo.deleteById(id);
    }
}