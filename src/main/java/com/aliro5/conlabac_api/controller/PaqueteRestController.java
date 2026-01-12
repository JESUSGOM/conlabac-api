package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Paquete;
import com.aliro5.conlabac_api.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paqueteria")
public class PaqueteRestController {

    @Autowired
    private PaqueteService service;

    // GET Pendientes
    @GetMapping("/pendientes")
    public List<Paquete> pendientes(@RequestParam("centroId") Integer centroId) {
        return service.listarPendientes(centroId);
    }

    // GET Histórico
    @GetMapping("/historial")
    public List<Paquete> historial(@RequestParam("centroId") Integer centroId) {
        return service.listarTodos(centroId);
    }

    // POST Nuevo Paquete
    @PostMapping
    public Paquete recibir(@RequestBody Paquete paquete) {
        return service.recibirPaquete(paquete);
    }

    // PUT Marcar Entregado
    @PutMapping("/{id}/entregar")
    public ResponseEntity<Void> entregar(@PathVariable Integer id) {
        service.marcarComoEntregado(id);
        return ResponseEntity.ok().build();
    }
}