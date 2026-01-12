package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Centro;
import com.aliro5.conlabac_api.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/centros")
public class CentroRestController {

    @Autowired
    private CentroService centroService;

    @GetMapping
    public List<Centro> listarCentros() {
        return centroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centro> obtenerCentro(@PathVariable Integer id) {
        Optional<Centro> centro = centroService.obtenerPorId(id);

        if (centro.isPresent()) {
            return ResponseEntity.ok(centro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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