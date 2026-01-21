package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Paquete;
import com.aliro5.conlabac_api.repository.PaqueteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaqueteServiceTest {

    @Mock
    private PaqueteRepository repo;

    @InjectMocks
    private PaqueteService service;

    private Paquete pktMock;

    @BeforeEach
    void setUp() {
        pktMock = new Paquete();
        pktMock.setId(1);
        pktMock.setIdCentro(1);
        pktMock.setDestinatario("Juan Perez");
        pktMock.setComunicado("NO");
    }

    @Test
    @DisplayName("Debe rellenar fechas y estado al recibir un paquete")
    void testRecibirPaquete() {
        when(repo.save(any(Paquete.class))).thenReturn(pktMock);

        Paquete resultado = service.recibirPaquete(new Paquete());

        assertNotNull(resultado);
        verify(repo, times(1)).save(any(Paquete.class));
        assertEquals("NO", resultado.getComunicado());
    }

    @Test
    @DisplayName("Debe corregir el error de tipos usando Optional correctamente")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(pktMock));

        // Solución al error 'Incompatible types'
        Paquete encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Juan Perez", encontrado.getDestinatario());
    }
}