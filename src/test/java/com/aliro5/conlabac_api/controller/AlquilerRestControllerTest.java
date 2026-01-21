package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.service.AlquilerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AlquilerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlquilerService service;

    @Test
    @DisplayName("Debe registrar un alquiler correctamente")
    void testRegistrarAlquiler() throws Exception {
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCentro(1);

        // CORRECCIÓN: Se cambia 'setNombreArrendatario' por 'setEmpresa'
        // para coincidir con la entidad y la BD (AlqEmpresa)
        alquiler.setEmpresa("Test Empresa S.L.");

        when(service.guardar(any(Alquiler.class))).thenReturn(alquiler);

        mockMvc.perform(post("/api/alquileres")
                        // CORRECCIÓN: Se usa APPLICATION_JSON para enviar el objeto serializado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alquiler)))
                .andExpect(status().isOk());
    }
}