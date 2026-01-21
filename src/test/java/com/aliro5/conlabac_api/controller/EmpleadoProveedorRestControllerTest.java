package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmpleadoProveedorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/empleados-proveedores: Debe crear un empleado externo")
    void crearEmpleadoOk() throws Exception {
        EmpleadoProveedor nuevo = new EmpleadoProveedor();
        nuevo.setNombre("Ana");
        nuevo.setApellido1("López");
        nuevo.setCifProveedor("A87654321");
        nuevo.setIdCentro(1);
        nuevo.setFechaAcceso(LocalDate.of(2026, 1, 1));

        mockMvc.perform(post("/api/empleados-proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET /api/empleados-proveedores/buscar: Debe filtrar por CIF y Centro")
    void buscarEmpleadosOk() throws Exception {
        mockMvc.perform(get("/api/empleados-proveedores/buscar")
                        .param("cif", "B12345678")
                        .param("centroId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}