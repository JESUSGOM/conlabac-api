package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoRestController {

    @Autowired
    private MovimientoService servicio;

    @GetMapping
    public List<Movimiento> listar() {
        return servicio.listarTodos();
    }

    @PostMapping
    public Movimiento guardar(@RequestBody Movimiento movimiento) {
        return servicio.guardar(movimiento);
    }

    @GetMapping("/{id}")
    public Movimiento obtener(@PathVariable Integer id) {
        return servicio.obtenerPorId(id).orElse(null);
    }

    // GET /api/movimientos/activos?centro=1
    @GetMapping("/activos")
    public List<Movimiento> listarActivosHoy(@RequestParam("centro") Integer idCentro) {
        return servicio.listarActivosHoyPorCentro(idCentro);
    }

    // PUT /api/movimientos/{id}/salida
    @PutMapping("/{id}/salida")
    public ResponseEntity<Void> registrarSalida(@PathVariable Integer id) {
        servicio.registrarSalida(id);
        return ResponseEntity.ok().build();
    }
}