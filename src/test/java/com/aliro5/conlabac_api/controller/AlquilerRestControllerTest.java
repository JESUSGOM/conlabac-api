package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.service.AlquilerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AlquilerRestController.class) // Solo carga la capa web
public class AlquilerRestControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    @MockBean
    private AlquilerService service; // Simula el servicio para no tocar la DB real

    @Test
    void testListarPorCentroDebeRetornarOk() throws Exception {
        // Configuramos el comportamiento esperado del servicio
        when(service.listarPorCentro(1)).thenReturn(Collections.emptyList());

        // Ejecutamos la petición GET y verificamos el status 200 OK
        mockMvc.perform(get("/api/alquileres/centro/1")
                        .contentType(MediaType.APPLICATION_BITSTREAM))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}