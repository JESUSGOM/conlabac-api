package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Planta;
import com.aliro5.conlabac_api.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantas")
public class PlantaRestController {

    @Autowired
    private PlantaService service;

    /**
     * Endpoint para el combo del Frontend: GET http://localhost:8080/api/plantas?centroId=1
     */
    @GetMapping
    public List<Planta> listar(@RequestParam(value = "centroId", required = false) Integer idCentro) {
        if (idCentro != null) {
            return service.listarPorCentro(idCentro);
        }
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planta> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Planta guardar(@RequestBody Planta planta) {
        return service.guardar(planta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}