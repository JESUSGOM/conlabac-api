package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {

    private static final Logger logger = LoggerFactory.getLogger(LlaveService.class);

    @Autowired
    private LlaveRepository llaveRepository;

    /**
     * Obtiene todas las llaves de un centro para el inventario.
     */
    public List<Llave> listarPorCentro(Integer idCentro) {
        logger.info("Buscando inventario completo de llaves para el centro: {}", idCentro);
        return llaveRepository.findByIdCentro(idCentro);
    }

    /**
     * Obtiene solo las llaves que no están prestadas actualmente.
     */
    public List<Llave> listarDisponibles(Integer idCentro) {
        logger.info("Buscando llaves disponibles para préstamo en centro: {}", idCentro);
        return llaveRepository.findDisponiblesPorCentro(idCentro);
    }

    /**
     * Busca una llave por su código alfanumérico (Ej: K-01).
     */
    public Llave obtenerPorCodigo(String codigo) {
        return llaveRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    logger.warn("No se encontró la llave con código: {}", codigo);
                    return null;
                });
    }

    public Llave guardar(Llave llave) {
        return llaveRepository.save(llave);
    }

    public Optional<Llave> obtenerPorId(Integer id) {
        return llaveRepository.findById(id);
    }

    public void eliminar(Integer id) {
        llaveRepository.deleteById(id);
    }
}