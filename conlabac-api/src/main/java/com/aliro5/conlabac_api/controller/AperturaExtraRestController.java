package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.service.AperturaExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperturas")
public class AperturaExtraRestController {

    @Autowired
    private AperturaExtraService service;

    @GetMapping
    public List<AperturaExtra> listar(@RequestParam("centroId") Integer centroId) {
        return service.listar(centroId);
    }

    @PostMapping
    public AperturaExtra guardar(@RequestBody AperturaExtra ae) {
        return service.guardar(ae);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}