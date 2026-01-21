package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios-email")
public class EnvioEmailRestController {

    @Autowired
    private EnvioEmailService service;

    @GetMapping
    public List<EnvioEmail> listar() {
        return service.listarTodo();
    }

    @GetMapping("/destinatario/{email}")
    public List<EnvioEmail> buscarPorDestinatario(@PathVariable String email) {
        return service.listarPorDestinatario(email);
    }
}