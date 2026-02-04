package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.service.GarajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/garaje")
public class GarajeRestController {

    @Autowired
    private GarajeService service;

    /**
     * Devuelve la lista de vehículos que están actualmente en el garaje
     * (aquellos que no tienen fecha de salida).
     */
    @GetMapping
    public List<Garaje> listar() {
        // Usamos el nuevo método del service que filtra los activos
        return service.listarActivos();
    }

    /**
     * Registra la entrada de un nuevo vehículo.
     */
    @PostMapping
    public Garaje guardar(@RequestBody Garaje registro) {
        return service.guardar(registro);
    }

    /**
     * Obtiene los detalles de un registro específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Garaje> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * NUEVO MÉTODO: Registra la salida de un vehículo.
     * Se invoca desde la web al pulsar el botón "Salida".
     */
    @PutMapping("/salida/{id}")
    public ResponseEntity<Void> registrarSalida(@PathVariable Integer id) {
        try {
            service.registrarSalida(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error al registrar salida en API: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}