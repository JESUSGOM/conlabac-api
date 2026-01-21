package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Contacto;
import com.aliro5.conlabac_api.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    @Autowired
    private ContactoService service;

    @GetMapping
    public List<Contacto> listar(@RequestParam("centroId") Integer centroId) {
        return service.listar(centroId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto) {
        return service.guardar(contacto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}