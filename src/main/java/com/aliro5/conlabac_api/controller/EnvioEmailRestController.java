package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.model.dto.EmailRequestDTO;
import com.aliro5.conlabac_api.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios-email")
public class EnvioEmailRestController {

    @Autowired
    private EnvioEmailService service;

    // MODIFICADO: Ahora requiere centroId
    @GetMapping("/paginado")
    public Page<EnvioEmail> listarPaginado(
            @RequestParam Integer centroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listarPaginado(centroId, PageRequest.of(page, size));
    }

    @GetMapping
    public List<EnvioEmail> listar() {
        return service.listarTodo();
    }

    @GetMapping("/destinatario/{email}")
    public List<EnvioEmail> buscarPorDestinatario(@PathVariable String email) {
        return service.listarPorDestinatario(email);
    }

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarNuevoEmail(@RequestBody EmailRequestDTO request) {
        try {
            service.enviarEmailLibre(request);
            return ResponseEntity.ok("{\"mensaje\": \"Email enviado correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/incidencia")
    public ResponseEntity<?> enviarIncidencia(@RequestBody EmailRequestDTO request) {
        try {
            service.enviarYRegistrarIncidencia(request);
            return ResponseEntity.ok("{\"mensaje\": \"Incidencia comunicada correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}