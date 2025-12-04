package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.aliro5.conlabac_api.model.Proveedor;
import com.aliro5.conlabac_api.repository.EmpleadoProveedorRepository;
import com.aliro5.conlabac_api.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorRestController {

    @Autowired
    private ProveedorService service;

    @Autowired
    private EmpleadoProveedorRepository empleadoRepo;

    // 1. Listar Proveedores
    @GetMapping
    public List<Proveedor> listar(@RequestParam("centroId") Integer centroId) {
        return service.listarProveedores(centroId);
    }

    // 2. Obtener un Proveedor (y sus empleados opcionalmente, pero haremos endpoints separados)
    @GetMapping("/{cif}")
    public ResponseEntity<Proveedor> obtener(@PathVariable String cif, @RequestParam("centroId") Integer centroId) {
        return service.obtenerProveedor(cif, centroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Guardar Proveedor
    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return service.guardarProveedor(proveedor);
    }

    // --- SUB-RECURSO: EMPLEADOS ---

    // 4. Listar empleados de un proveedor
    @GetMapping("/{cif}/empleados")
    public List<EmpleadoProveedor> listarEmpleados(@PathVariable String cif, @RequestParam("centroId") Integer centroId) {
        return service.listarEmpleados(cif, centroId);
    }

    // 5. Guardar Empleado
    @PostMapping("/empleados")
    public EmpleadoProveedor guardarEmpleado(@RequestBody EmpleadoProveedor empleado) {
        return service.guardarEmpleado(empleado);
    }

    // 6. Eliminar Empleado
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        service.eliminarEmpleado(id);
        return ResponseEntity.ok().build();
    }

    // 7. Listar TODOS los empleados de un centro (Global)
    @GetMapping("/empleados/todos")
    public List<EmpleadoProveedor> listarTodosEmpleados(@RequestParam("centroId") Integer centroId) {
        return empleadoRepo.findByIdCentro(centroId);
    }
}