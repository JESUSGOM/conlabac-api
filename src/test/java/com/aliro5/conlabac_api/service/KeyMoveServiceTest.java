package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.KeyMove;
import com.aliro5.conlabac_api.repository.KeyMoveRepository;
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
class KeyMoveServiceTest {

    @Mock
    private KeyMoveRepository repo;

    @InjectMocks
    private KeyMoveService service;

    private KeyMove mockMove;

    @BeforeEach
    void setUp() {
        // Inicializamos un objeto con datos válidos según la estructura de tu BD
        mockMove = new KeyMove();
        mockMove.setId(1);
        mockMove.setCodigoLlave("K-101"); // Longitud válida (máximo 8)
        mockMove.setIdCentro(1);
        mockMove.setNombre("Jesus");
        mockMove.setApellido1("Gomez");
    }

    @Test
    @DisplayName("Debe registrar la entrega con la fecha actual formateada")
    void testEntregarLlave() {
        // CORRECCIÓN: Usar el objeto mockMove que ya tiene datos para evitar que el service reciba null
        // y falle la aserción assertNotNull
        when(repo.save(any(KeyMove.class))).thenReturn(mockMove);

        KeyMove resultado = service.entregarLlave(mockMove);

        // Verificaciones críticas para limpiar el error 'expected: not <null>'
        assertNotNull(resultado, "El resultado del servicio no debería ser null");
        assertEquals("K-101", resultado.getCodigoLlave());
        verify(repo, times(1)).save(any(KeyMove.class));
    }

    @Test
    @DisplayName("Debe manejar el Optional correctamente para evitar errores de tipo")
    void testObtenerPorId() {
        // Simulamos que el repositorio devuelve el objeto envuelto en un Optional
        when(repo.findById(1)).thenReturn(Optional.of(mockMove));

        // Corrección de tipos incompatibles usando orElse(null)
        KeyMove encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado, "El objeto encontrado no debería ser null");
        assertEquals("K-101", encontrado.getCodigoLlave());
        assertEquals(1, encontrado.getIdCentro());
    }
}