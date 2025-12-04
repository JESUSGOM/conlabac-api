package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaRestController {

    @Autowired
    private IncidenciaService servicio;

    // GET /api/incidencias?centroId=1
    @GetMapping
    public List<Incidencia> listar(@RequestParam("centroId") Integer centroId) {
        return servicio.listarPorCentro(centroId);
    }

    // GET /api/incidencias/{id}
    @GetMapping("/{id}")
    public Incidencia obtener(@PathVariable Integer id) {
        return servicio.obtenerPorId(id).orElse(null);
    }

    // POST /api/incidencias
    @PostMapping
    public Incidencia guardar(@RequestBody Incidencia incidencia) {
        return servicio.guardar(incidencia);
    }
}