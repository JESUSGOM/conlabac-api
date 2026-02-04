package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MovimientoRestController {

    @Autowired
    private MovimientoService servicio;

    @GetMapping("/movimientos")
    public ResponseEntity<List<Movimiento>> listar() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @PostMapping("/movimientos")
    public ResponseEntity<Movimiento> guardar(@RequestBody Movimiento movimiento) {
        // Log para depuración
        System.out.println("API: Recibida petición para guardar movimiento de: " + movimiento.getNombre());
        return ResponseEntity.ok(servicio.guardar(movimiento));
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<Movimiento> obtener(@PathVariable Integer id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Movimiento>> listarActivosHoy(@RequestParam("centro") Integer idCentro) {
        System.out.println("API: Buscando visitantes activos para centro: " + idCentro);
        List<Movimiento> activos = servicio.listarActivosHoyPorCentro(idCentro);
        return ResponseEntity.ok(activos != null ? activos : List.of());
    }

    @PutMapping("/movimientos/{id}/salida")
    public ResponseEntity<Void> registrarSalida(@PathVariable Integer id) {
        try {
            servicio.registrarSalida(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("API ERROR: Fallo al registrar salida ID " + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}