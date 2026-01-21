package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.service.LlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/llaves")
public class LlaveRestController {

    @Autowired
    private LlaveService llaveService;

    /**
     * Inventario completo por centro
     * GET /api/llaves?centroId=1
     */
    @GetMapping
    public List<Llave> listarPorCentro(@RequestParam("centroId") Integer centroId) {
        return llaveService.listarPorCentro(centroId);
    }

    /**
     * Llaves en el tablero listas para entregar
     * GET /api/llaves/disponibles?centroId=1
     */
    @GetMapping("/disponibles")
    public List<Llave> listarDisponibles(@RequestParam("centroId") Integer centroId) {
        return llaveService.listarDisponibles(centroId);
    }

    @PostMapping
    public Llave guardar(@RequestBody Llave llave) {
        return llaveService.guardar(llave);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        llaveService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}