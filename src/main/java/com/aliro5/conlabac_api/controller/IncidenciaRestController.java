package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaRestController {

    @Autowired
    private IncidenciaService servicio;

    @GetMapping("/paginado")
    public ResponseEntity<Page<Incidencia>> listarPaginado(
            @RequestParam("centroId") Integer centroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(servicio.listarPaginado(centroId, PageRequest.of(page, size)));
    }

    @GetMapping
    public ResponseEntity<List<Incidencia>> listar(@RequestParam("centroId") Integer centroId) {
        return ResponseEntity.ok(servicio.listarPorCentro(centroId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtener(@PathVariable Integer id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Incidencia> guardar(@RequestBody Incidencia incidencia) {
        return ResponseEntity.ok(servicio.guardar(incidencia));
    }
}