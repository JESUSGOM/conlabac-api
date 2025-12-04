package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.service.LlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/llaves")
public class LlaveRestController {

    @Autowired
    private LlaveService llaveService;

    @GetMapping
    public List<Llave> listarPorCentro(@RequestParam("centroId") Integer centroId) {
        return llaveService.listarPorCentro(centroId);
    }
}