package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.aliro5.conlabac_api.service.EmpleadoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados-proveedores")
public class EmpleadoProveedorRestController {

    @Autowired
    private EmpleadoProveedorService service;

    /**
     * Obtiene todos los empleados de un centro.
     * GET /api/empleados-proveedores?centroId=1
     */
    @GetMapping
    public List<EmpleadoProveedor> listarPorCentro(@RequestParam Integer centroId) {
        return service.listarPorCentro(centroId);
    }

    /**
     * Busca empleados por CIF y Centro (usado por el Test de Integración).
     * GET /api/empleados-proveedores/buscar?cif=B12345678&centroId=1
     */
    @GetMapping("/buscar")
    public List<EmpleadoProveedor> buscar(@RequestParam String cif, @RequestParam Integer centroId) {
        return service.listarPorProveedorEnCentro(cif, centroId);
    }

    /**
     * Obtiene un empleado específico por su ID.
     * GET /api/empleados-proveedores/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoProveedor> obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea o actualiza un empleado.
     * POST /api/empleados-proveedores
     */
    @PostMapping
    public EmpleadoProveedor guardar(@RequestBody EmpleadoProveedor empleado) {
        return service.guardar(empleado);
    }

    /**
     * Elimina un empleado.
     * DELETE /api/empleados-proveedores/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}