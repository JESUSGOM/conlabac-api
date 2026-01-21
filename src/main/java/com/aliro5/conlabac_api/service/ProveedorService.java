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

    @Autowired
    private ProveedorRepository proveedorRepo;

    @Autowired
    private EmpleadoProveedorRepository empleadoRepo;

    // --- MÉTODOS DE PROVEEDORES ---

    /**
     * Lista todos los proveedores asociados a un centro específico.
     */
    public List<Proveedor> listarPorCentro(Integer idCentro) {
        return proveedorRepo.findByIdCentro(idCentro);
    }

    /**
     * Obtiene un proveedor por su clave compuesta (CIF + ID Centro).
     * Esto soluciona errores de tipos incompatibles en controladores y tests.
     */
    public Optional<Proveedor> obtenerPorId(String cif, Integer idCentro) {
        return proveedorRepo.findById(new ProveedorId(cif, idCentro));
    }

    /**
     * Guarda o actualiza un proveedor, asignando fecha de alta si no existe.
     */
    public Proveedor guardar(Proveedor p) {
        if (p.getFechaAlta() == null) {
            p.setFechaAlta(LocalDate.now());
        }
        return proveedorRepo.save(p);
    }

    /**
     * Elimina un proveedor por su clave compuesta.
     */
    public void eliminar(String cif, Integer idCentro) {
        proveedorRepo.deleteById(new ProveedorId(cif, idCentro));
    }

    // --- MÉTODOS DE EMPLEADOS DE PROVEEDOR (CORRECCIÓN image_612b4d.png) ---

    /**
     * Lista los empleados de un proveedor específico en un centro.
     */
    public List<EmpleadoProveedor> listarEmpleados(String cif, Integer idCentro) {
        return empleadoRepo.findByCifProveedorAndIdCentro(cif, idCentro);
    }

    /**
     * Registra un nuevo empleado para un proveedor.
     */
    public EmpleadoProveedor guardarEmpleado(EmpleadoProveedor emp) {
        return empleadoRepo.save(emp);
    }

    /**
     * Elimina un empleado por su ID primario.
     */
    public void eliminarEmpleado(Integer id) {
        empleadoRepo.deleteById(id);
    }
}