package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.service.GarajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/garaje")
public class GarajeRestController {

    @Autowired
    private GarajeService service;

    @GetMapping
    public List<Garaje> listar() {
        // En el log se veía que buscabas listar por centro,
        // si tu service tiene listarPorCentro(id), deberías usarlo aquí.
        return service.listarTodos();
    }

    @PostMapping
    public Garaje guardar(@RequestBody Garaje registro) {
        return service.guardar(registro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garaje> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}