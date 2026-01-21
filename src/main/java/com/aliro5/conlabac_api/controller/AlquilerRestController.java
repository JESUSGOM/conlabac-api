package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class AlquilerRestController {

    @Autowired
    private AlquilerService service;

    /**
     * Endpoint para el combo del Frontend: GET http://localhost:8080/api/destinos?centroId=1
     */
    @GetMapping
    public List<Alquiler> listarPorCentro(@RequestParam("centroId") Integer centroId) {
        return service.listarPorCentro(centroId);
    }

    @PostMapping
    public ResponseEntity<Alquiler> guardar(@RequestBody Alquiler alquiler) {
        return ResponseEntity.ok(service.guardar(alquiler));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}