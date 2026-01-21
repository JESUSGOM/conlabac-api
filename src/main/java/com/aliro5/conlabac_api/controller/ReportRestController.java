package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.service.InformeMensualService;
import com.aliro5.conlabac_api.service.MovimientoService;
import com.aliro5.conlabac_api.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired private MovimientoService movimientoService;
    @Autowired private PdfService pdfService;
    @Autowired private InformeMensualService informeService;

    @GetMapping("/movimientos/pdf")
    public ResponseEntity<byte[]> descargarPdfMovimientos(@RequestParam("centroId") Integer centroId) {
        List<Movimiento> lista = movimientoService.listarActivosHoyPorCentro(centroId);
        byte[] pdfBytes = pdfService.generarReporteMovimientos(lista, "Reporte de Movimientos Activos");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movimientos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/mensual")
    public ResponseEntity<byte[]> informeMensual(
            @RequestParam int mes,
            @RequestParam int anio,
            @RequestParam int centroId) {

        byte[] pdf = informeService.generarInforme(mes, anio, centroId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Informe_Mensual_" + mes + "_" + anio + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}