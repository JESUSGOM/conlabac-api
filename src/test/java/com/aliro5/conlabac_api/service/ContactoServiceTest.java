package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Contacto;
import com.aliro5.conlabac_api.repository.ContactoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections; // Cambiado para evitar warnings de asList
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactoServiceTest {

    @Mock
    private ContactoRepository repo;

    @InjectMocks
    private ContactoService service;

    private Contacto contactoMock;

    @BeforeEach
    void setUp() {
        contactoMock = new Contacto();
        contactoMock.setId(1);
        contactoMock.setNombre("Juan Perez");
        contactoMock.setIdCentro(1);
    }

    @Test
    @DisplayName("Debe listar contactos por centro usando el alias findByIdCentro")
    void testFindByIdCentro() {
        // CORRECCIÓN: Ahora el repo ya reconoce findByIdCentro
        // Usamos Collections.singletonList para evitar el warning de asList() :42
        when(repo.findByIdCentro(1)).thenReturn(Collections.singletonList(contactoMock));

        List<Contacto> resultado = service.findByIdCentro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo).findByIdCentro(1);
    }

    @Test
    @DisplayName("Debe listar contactos usando el alias listarPorCentro")
    void testListarPorCentro() {
        // Usamos Collections.singletonList para evitar el warning de asList() :56
        when(repo.findByIdCentro(1)).thenReturn(Collections.singletonList(contactoMock));

        List<Contacto> resultado = service.listarPorCentro(1);

        assertFalse(resultado.isEmpty());
        verify(repo, times(1)).findByIdCentro(1);
    }
}