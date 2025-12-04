package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.aliro5.conlabac_api.service.EntreTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entreturnos")
public class EntreTurnoRestController {

    @Autowired
    private EntreTurnoService servicio;

    // GET /api/entreturnos/historial?centroId=1
    @GetMapping("/historial")
    public List<EntreTurno> historial(@RequestParam("centroId") Integer centroId) {
        return servicio.listarHistorial(centroId);
    }

    // GET /api/entreturnos/pendientes?centroId=1
    @GetMapping("/pendientes")
    public List<EntreTurno> pendientes(@RequestParam("centroId") Integer centroId) {
        return servicio.listarPendientes(centroId);
    }

    // POST /api/entreturnos (Crear)
    @PostMapping
    public EntreTurno crear(@RequestBody EntreTurno nota) {
        return servicio.crear(nota);
    }

    // PUT /api/entreturnos/{id}/leer (Marcar leído)
    // Espera un JSON simple: { "lector": "Nombre Usuario" }
    @PutMapping("/{id}/leer")
    public ResponseEntity<Void> marcarLeido(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String lector = body.get("lector");
        servicio.marcarLeido(id, lector);
        return ResponseEntity.ok().build();
    }
}