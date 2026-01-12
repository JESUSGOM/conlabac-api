package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Telefono;
import com.aliro5.conlabac_api.service.TelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telefonos")
public class TelefonoRestController {

    @Autowired
    private TelefonoService service;

    @GetMapping("/pendientes")
    public List<Telefono> pendientes(@RequestParam("centroId") Integer centroId) {
        return service.listarPendientes(centroId);
    }

    @GetMapping("/historial")
    public List<Telefono> historial(@RequestParam("centroId") Integer centroId) {
        return service.listarHistorial(centroId);
    }

    @PostMapping
    public Telefono registrar(@RequestBody Telefono telefono) {
        return service.registrar(telefono);
    }

    @PutMapping("/{id}/comunicar")
    public ResponseEntity<Void> comunicar(@PathVariable Integer id) {
        service.marcarComunicado(id);
        return ResponseEntity.ok().build();
    }
}