package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Telefono;
import com.aliro5.conlabac_api.service.TelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telefonos")
@CrossOrigin(origins = "*") // Permite la conexión desde el puerto 8081
public class TelefonoRestController {

    @Autowired
    private TelefonoService service;

    @GetMapping("/pendientes")
    public ResponseEntity<List<Telefono>> pendientes(@RequestParam("centroId") Integer centroId) {
        List<Telefono> lista = service.listarPendientes(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Telefono>> historial(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Consultando historial de telefonos para centro: " + centroId);
        List<Telefono> lista = service.listarHistorial(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    @PostMapping
    public ResponseEntity<Telefono> registrar(@RequestBody Telefono telefono) {
        return ResponseEntity.ok(service.registrar(telefono));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telefono> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/comunicar")
    public ResponseEntity<Void> comunicar(@PathVariable Integer id) {
        try {
            service.marcarComunicado(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}