package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.aliro5.conlabac_api.service.EntreTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entre-turnos")
@CrossOrigin(origins = "*") // Permite la conexión desde el puerto 8081 (Web)
public class EntreTurnoRestController {

    @Autowired
    private EntreTurnoService servicio;

    @GetMapping("/historial")
    public ResponseEntity<List<EntreTurno>> historial(@RequestParam("centroId") Integer centroId) {
        System.out.println("API: Consultando historial de relevos para centro: " + centroId);
        List<EntreTurno> lista = servicio.listarHistorial(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<EntreTurno>> pendientes(@RequestParam("centroId") Integer centroId) {
        List<EntreTurno> lista = servicio.listarPendientes(centroId);
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    @PostMapping
    public ResponseEntity<EntreTurno> crear(@RequestBody EntreTurno nota) {
        return ResponseEntity.ok(servicio.guardar(nota));
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<Void> marcarLeido(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String lector = body.get("lector");
            servicio.marcarLeido(id, lector);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}