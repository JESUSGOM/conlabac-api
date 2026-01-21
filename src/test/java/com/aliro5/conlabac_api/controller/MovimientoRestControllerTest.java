package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Movimiento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovimientoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/movimientos: Debe crear un movimiento correctamente")
    void crearMovimientoOk() throws Exception {
        Movimiento nuevo = new Movimiento();
        nuevo.setIdCentro(1);
        nuevo.setNombre("Ana");
        nuevo.setApellido1("Sanz");
        nuevo.setFechaEntrada(LocalDateTime.now());
        nuevo.setProcedencia("Visita");

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET /api/movimientos/activos: Debe retornar status 200 con parámetro centro")
    void listarActivosOk() throws Exception {
        mockMvc.perform(get("/api/movimientos/activos")
                        .param("centro", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/movimientos/{id}/salida: Debe registrar salida exitosa")
    void registrarSalidaOk() throws Exception {
        mockMvc.perform(put("/api/movimientos/1/salida")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}