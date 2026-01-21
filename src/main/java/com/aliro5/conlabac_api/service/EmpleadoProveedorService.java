package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.aliro5.conlabac_api.repository.EmpleadoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoProveedorService {

    @Autowired
    private EmpleadoProveedorRepository repo;

    /**
     * Lista empleados filtrando por empresa (CIF) y por centro de trabajo.
     * Utilizado por el test: testListarPorCifYCentro
     */
    public List<EmpleadoProveedor> listarPorProveedorEnCentro(String cif, Integer idCentro) {
        return repo.findByCifProveedorAndIdCentro(cif, idCentro);
    }

    /**
     * Lista todos los empleados asignados a un centro, sin importar su empresa.
     */
    public List<EmpleadoProveedor> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    /**
     * Obtiene un empleado por su ID.
     * Retorna Optional para gestionar de forma segura los valores nulos en los tests.
     */
    public Optional<EmpleadoProveedor> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    /**
     * Guarda o actualiza un empleado de proveedor.
     */
    @Transactional
    public EmpleadoProveedor guardar(EmpleadoProveedor empleado) {
        return repo.save(empleado);
    }

    /**
     * Elimina un empleado del sistema.
     */
    @Transactional
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Busca un empleado por su NIF (DNI/NIE).
     */
    public Optional<EmpleadoProveedor> buscarPorNif(String nif) {
        return repo.findByNif(nif);
    }
}