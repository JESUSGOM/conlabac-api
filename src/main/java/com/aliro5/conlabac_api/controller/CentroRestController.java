package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Centro;
import com.aliro5.conlabac_api.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/centros")
@CrossOrigin(origins = "*") // Permite que la Web conecte sin bloqueos de navegador
public class CentroRestController {

    @Autowired
    private CentroService centroService;

    @GetMapping
    public List<Centro> listarCentros() {
        return centroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centro> obtenerCentro(@PathVariable Integer id) {
        return centroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Centro crearCentro(@RequestBody Centro centro) {
        return centroService.guardar(centro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCentro(@PathVariable Integer id) {
        centroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}