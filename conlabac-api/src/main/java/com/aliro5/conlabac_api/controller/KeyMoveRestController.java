package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.KeyMove;
import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
import com.aliro5.conlabac_api.service.KeyMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keymoves")
public class KeyMoveRestController {

    @Autowired
    private KeyMoveService keyMoveService;

    @Autowired
    private LlaveRepository llaveRepository;

    // GET: Ver préstamos activos
    @GetMapping("/activos")
    public List<KeyMove> listarActivos(@RequestParam("centroId") Integer centroId) {
        return keyMoveService.listarPrestamosActivos(centroId);
    }

    // GET: Ver llaves disponibles (Para el combo)
    @GetMapping("/disponibles")
    public List<Llave> listarDisponibles(@RequestParam("centroId") Integer centroId) {
        return llaveRepository.findDisponiblesPorCentro(centroId);
    }

    // POST: Entregar llave
    @PostMapping
    public KeyMove entregar(@RequestBody KeyMove keyMove) {
        return keyMoveService.entregarLlave(keyMove);
    }

    // PUT: Devolver llave
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Void> devolver(@PathVariable Integer id) {
        keyMoveService.devolverLlave(id);
        return ResponseEntity.ok().build();
    }

    // GET: Ver préstamos activos SOLO DE HOY
    @GetMapping("/activos-hoy")
    public List<KeyMove> listarActivosHoy(@RequestParam("centroId") Integer centroId) {
        return keyMoveService.listarPrestamosActivosHoy(centroId);
    }
}