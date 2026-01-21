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
@CrossOrigin(origins = "*") // Permite la comunicación entre puertos 8081 y 8080
public class ProveedorRestController {

    @Autowired
    private ProveedorService service;

    @Autowired
    private EmpleadoProveedorRepository empleadoRepo;

    // 1. Listar Proveedores por Centro
    @GetMapping
    public ResponseEntity<List<Proveedor>> listar(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Listando proveedores para centro: " + centroId);
        List<Proveedor> lista = service.listarPorCentro(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    // 2. Obtener un Proveedor (Cif + CentroId)
    @GetMapping("/{cif}")
    public ResponseEntity<Proveedor> obtener(@PathVariable String cif, @RequestParam("centroId") Integer centroId) {
        return service.obtenerPorId(cif, centroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Guardar Proveedor
    @PostMapping
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(service.guardar(proveedor));
    }

    // 4. Listar empleados de un proveedor específico
    @GetMapping("/{cif}/empleados")
    public ResponseEntity<List<EmpleadoProveedor>> listarEmpleados(@PathVariable String cif, @RequestParam("centroId") Integer centroId) {
        List<EmpleadoProveedor> lista = service.listarEmpleados(cif, centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    // 5. Guardar Empleado de Proveedor
    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoProveedor> guardarEmpleado(@RequestBody EmpleadoProveedor empleado) {
        return ResponseEntity.ok(service.guardarEmpleado(empleado));
    }

    // 6. Eliminar Empleado
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        try {
            service.eliminarEmpleado(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 7. Listar TODOS los empleados de un centro
    @GetMapping("/empleados/todos")
    public ResponseEntity<List<EmpleadoProveedor>> listarTodosEmpleados(@RequestParam("centroId") Integer centroId) {
        List<EmpleadoProveedor> lista = empleadoRepo.findByIdCentro(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }
}