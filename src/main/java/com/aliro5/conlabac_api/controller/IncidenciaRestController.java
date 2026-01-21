package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "*") // Crucial para la comunicación entre puertos 8081 y 8080
public class IncidenciaRestController {

    @Autowired
    private IncidenciaService servicio;

    @GetMapping
    public ResponseEntity<List<Incidencia>> listar(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Consultando incidencias para centro: " + centroId);
        List<Incidencia> lista = servicio.listarPorCentro(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
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