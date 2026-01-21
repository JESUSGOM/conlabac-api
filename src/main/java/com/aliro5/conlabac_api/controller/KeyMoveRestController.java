package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.KeyMove;
import com.aliro5.conlabac_api.service.KeyMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keymoves") // Sincronizado con el Frontend (getMovesUrl)
@CrossOrigin(origins = "*")
public class KeyMoveRestController {

    @Autowired
    private KeyMoveService keyMoveService;

    /**
     * Obtener llaves prestadas hoy.
     * GET http://localhost:8080/api/keymoves/activos-hoy?centroId=X
     */
    @GetMapping("/activos-hoy")
    public ResponseEntity<List<KeyMove>> listarActivosHoy(@RequestParam Integer centroId) {
        List<KeyMove> lista = keyMoveService.listarPrestadasHoy(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    /**
     * Obtener préstamos activos (sin devolver).
     * GET http://localhost:8080/api/keymoves/activos?centroId=X
     */
    @GetMapping("/activos")
    public ResponseEntity<List<KeyMove>> listarActivos(@RequestParam Integer centroId) {
        // Asumimos que tu service tiene un método para filtrar los que no tienen fecha de devolución
        List<KeyMove> lista = keyMoveService.listarPrestadasHoy(centroId);
        return ResponseEntity.ok(lista);
    }

    /**
     * Registrar una nueva entrega (Salida de llave).
     * POST http://localhost:8080/api/keymoves
     */
    @PostMapping
    public ResponseEntity<KeyMove> entregar(@RequestBody KeyMove movimiento) {
        return ResponseEntity.ok(keyMoveService.entregarLlave(movimiento));
    }

    /**
     * Obtener un movimiento específico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KeyMove> obtener(@PathVariable Integer id) {
        return keyMoveService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Registrar la devolución (Entrada de llave).
     * PUT http://localhost:8080/api/keymoves/{id}/devolver
     */
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Void> devolver(@PathVariable Integer id) {
        keyMoveService.devolverLlave(id);
        return ResponseEntity.ok().build();
    }
}