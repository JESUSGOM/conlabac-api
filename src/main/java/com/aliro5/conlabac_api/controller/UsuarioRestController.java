package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = service.listarTodos();
        return ResponseEntity.ok(lista != null ? lista : List.of());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> obtener(@PathVariable String dni) {
        return service.obtenerPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.guardar(usuario));
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni) {
        service.eliminar(dni);
        return ResponseEntity.ok().build();
    }
}