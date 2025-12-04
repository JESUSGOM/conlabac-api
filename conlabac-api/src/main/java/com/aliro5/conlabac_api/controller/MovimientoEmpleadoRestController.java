package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import com.aliro5.conlabac_api.service.MovimientoEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contratas")
public class MovimientoEmpleadoRestController {

    @Autowired
    private MovimientoEmpleadoService service;

    // GET: Ver quién está dentro
    @GetMapping("/activos")
    public List<MovimientoEmpleado> listarActivos(@RequestParam("centroId") Integer centroId) {
        return service.listarActivos(centroId);
    }

    // POST: Fichar Entrada
    // Espera JSON: { "nif": "1234X", "cif": "B123", "centroId": 1 }
    @PostMapping("/entrada")
    public MovimientoEmpleado entrada(@RequestBody Map<String, Object> datos) {
        String nif = (String) datos.get("nif");
        String cif = (String) datos.get("cif");
        // Aseguramos conversión segura a Integer
        Integer centroId = Integer.valueOf(datos.get("centroId").toString());

        return service.registrarEntrada(nif, cif, centroId);
    }

    // PUT: Fichar Salida (por ID de movimiento)
    @PutMapping("/salida/{id}")
    public ResponseEntity<Void> salida(@PathVariable Integer id) {
        service.registrarSalidaPorId(id);
        return ResponseEntity.ok().build();
    }
}