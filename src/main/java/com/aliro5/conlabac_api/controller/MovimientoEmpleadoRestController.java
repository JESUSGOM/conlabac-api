package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import com.aliro5.conlabac_api.service.MovimientoEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contratas")
@CrossOrigin(origins = "*") // Permite la conexión desde el navegador (puerto 8081)
public class MovimientoEmpleadoRestController {

    @Autowired
    private MovimientoEmpleadoService service;

    // GET: http://localhost:8080/api/contratas/activos?centroId=X
    @GetMapping("/activos")
    public ResponseEntity<List<MovimientoEmpleado>> listarActivos(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Consultando contratas activas para centro: " + centroId);
        List<MovimientoEmpleado> lista = service.listarActivos(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    // POST: http://localhost:8080/api/contratas/entrada
    @PostMapping("/entrada")
    public ResponseEntity<MovimientoEmpleado> entrada(@RequestBody Map<String, Object> datos) {
        try {
            String nif = (String) datos.get("nif");
            String cif = (String) datos.get("cif");
            Integer centroId = Integer.valueOf(datos.get("centroId").toString());
            return ResponseEntity.ok(service.registrarEntrada(nif, cif, centroId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT: http://localhost:8080/api/contratas/salida/{id}
    @PutMapping("/salida/{id}")
    public ResponseEntity<Void> salida(@PathVariable Integer id) {
        try {
            service.registrarSalidaPorId(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}