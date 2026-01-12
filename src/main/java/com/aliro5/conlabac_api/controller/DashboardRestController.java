package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.dto.DashboardStats;
import com.aliro5.conlabac_api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    @Autowired
    private DashboardService service;

    @GetMapping("/stats")
    public DashboardStats getStats(@RequestParam("centroId") Integer centroId) {
        return service.obtenerEstadisticas(centroId);
    }
}