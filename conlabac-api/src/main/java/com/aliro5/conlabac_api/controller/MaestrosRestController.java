package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.model.Planta;
import com.aliro5.conlabac_api.repository.AlquilerRepository;
import com.aliro5.conlabac_api.repository.PlantaRepository;
import com.aliro5.conlabac_api.service.AlquilerService;
import com.aliro5.conlabac_api.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maestros")
public class MaestrosRestController {

    @Autowired private AlquilerService alquilerService;
    @Autowired private PlantaService plantaService;

    // --- GESTIÓN DE ALQUILERES (EMPRESAS INQUILINAS) ---

    @GetMapping("/alquileres")
    public List<Alquiler> listarAlquileres(@RequestParam("centroId") Integer centroId) {
        return alquilerService.listarPorCentro(centroId);
    }

    // Mantenemos este endpoint antiguo por compatibilidad con el JS del combo (si se usaba así)
    // O lo redirigimos al servicio nuevo
    @GetMapping("/destinos/{centroId}")
    public List<Alquiler> getDestinosLegacy(@PathVariable Integer centroId) {
        return alquilerService.listarPorCentro(centroId);
    }

    @PostMapping("/alquileres")
    public Alquiler guardarAlquiler(@RequestBody Alquiler alquiler) {
        return alquilerService.guardar(alquiler);
    }

    @DeleteMapping("/alquileres/{id}")
    public ResponseEntity<Void> eliminarAlquiler(@PathVariable Integer id) {
        alquilerService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    // --- GESTIÓN DE PLANTAS ---

    @GetMapping("/plantas")
    public List<Planta> listarPlantas(@RequestParam("centroId") Integer centroId) {
        return plantaService.listarPorCentro(centroId);
    }

    // Endpoint legacy por compatibilidad
    @GetMapping("/plantas/{centroId}")
    public List<Planta> getPlantasLegacy(@PathVariable Integer centroId) {
        return plantaService.listarPorCentro(centroId);
    }

    @PostMapping("/plantas")
    public Planta guardarPlanta(@RequestBody Planta planta) {
        return plantaService.guardar(planta);
    }

    @DeleteMapping("/plantas/{id}")
    public ResponseEntity<Void> eliminarPlanta(@PathVariable Integer id) {
        plantaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}