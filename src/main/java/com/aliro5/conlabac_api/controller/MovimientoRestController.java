package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Ruta base simplificada para coincidir con el Frontend
@CrossOrigin(origins = "*")
public class MovimientoRestController {

    @Autowired
    private MovimientoService servicio;

    // Ruta: GET http://localhost:8080/api/movimientos
    @GetMapping("/movimientos")
    public ResponseEntity<List<Movimiento>> listar() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    // Ruta: POST http://localhost:8080/api/movimientos
    @PostMapping("/movimientos")
    public ResponseEntity<Movimiento> guardar(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(servicio.guardar(movimiento));
    }

    // Ruta: GET http://localhost:8080/api/movimientos/{id}
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<Movimiento> obtener(@PathVariable Integer id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * CRÍTICO: Esta es la ruta que tu Web está buscando.
     * URL: http://localhost:8080/api/activos?centro=X
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Movimiento>> listarActivosHoy(@RequestParam("centro") Integer idCentro) {
        System.out.println("API: Buscando visitantes activos para centro: " + idCentro);
        List<Movimiento> activos = servicio.listarActivosHoyPorCentro(idCentro);
        return ResponseEntity.ok(activos != null ? activos : List.of());
    }

    // Ruta: PUT http://localhost:8080/api/movimientos/{id}/salida
    @PutMapping("/movimientos/{id}/salida")
    public ResponseEntity<Void> registrarSalida(@PathVariable Integer id) {
        try {
            servicio.registrarSalida(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}