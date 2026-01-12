package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerRestController {

    @Autowired
    private AlquilerService service;

    @GetMapping("/centro/{idCentro}")
    public List<Alquiler> listarPorCentro(@PathVariable Integer idCentro) {
        return service.listarPorCentro(idCentro);
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