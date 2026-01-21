package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Paquete;
import com.aliro5.conlabac_api.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paqueteria")
@CrossOrigin(origins = "*") // Permite la comunicación desde el puerto 8081
public class PaqueteRestController {

    @Autowired
    private PaqueteService service;

    // GET Pendientes: http://localhost:8080/api/paqueteria/pendientes?centroId=X
    @GetMapping("/pendientes")
    public ResponseEntity<List<Paquete>> pendientes(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Buscando paquetes pendientes para centro: " + centroId);
        List<Paquete> lista = service.listarPendientes(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    // GET Histórico: http://localhost:8080/api/paqueteria/historial?centroId=X
    @GetMapping("/historial")
    public ResponseEntity<List<Paquete>> historial(@RequestParam("centroId") Integer centroId) {
        List<Paquete> lista = service.listarTodos(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    // POST Nuevo Paquete
    @PostMapping
    public ResponseEntity<Paquete> recibir(@RequestBody Paquete paquete) {
        return ResponseEntity.ok(service.recibirPaquete(paquete));
    }

    // PUT Marcar Entregado
    @PutMapping("/{id}/entregar")
    public ResponseEntity<Void> entregar(@PathVariable Integer id) {
        try {
            service.marcarComoEntregado(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}