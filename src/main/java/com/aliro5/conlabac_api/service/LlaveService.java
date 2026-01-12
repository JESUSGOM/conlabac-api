package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {

    @Autowired
    private LlaveRepository llaveRepository;

    public List<Llave> listarPorCentro(Integer idCentro) {
        return llaveRepository.findByIdCentro(idCentro);
    }

    public List<Llave> listarDisponibles(Integer idCentro) {
        return llaveRepository.findDisponiblesPorCentro(idCentro);
    }

    // NUEVO: Método para buscar por código de llave
    // El test LlaveServiceTest depende de que este método exista.
    public Llave obtenerPorCodigo(String codigo) {
        return llaveRepository.findByCodigo(codigo).orElse(null);
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