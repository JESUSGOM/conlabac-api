package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.service.AperturaExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperturas-extra") // Ruta unificada para evitar 404/500
public class AperturaExtraRestController {

    @Autowired
    private AperturaExtraService service;

    /**
     * GET http://localhost:8080/api/aperturas-extra?centroId=1
     */
    @GetMapping
    public List<AperturaExtra> listar(@RequestParam("centroId") Integer centroId) {
        return service.listarPorCentro(centroId);
    }

    /**
     * POST http://localhost:8080/api/aperturas-extra
     * El @RequestBody convierte el JSON de la WEB en un objeto Java
     */
    @PostMapping
    public AperturaExtra guardar(@RequestBody AperturaExtra ae) {
        System.out.println(">>> API: Recibida solicitud para guardar apertura extra.");
        return service.guardar(ae);
    }

    /**
     * DELETE http://localhost:8080/api/aperturas-extra/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}