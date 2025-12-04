package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Centro;
import com.aliro5.conlabac_api.repository.CentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CentroService {

    @Autowired
    private CentroRepository centroRepository;

    public List<Centro> listarTodos() {
        return centroRepository.findAll();
    }

    public Optional<Centro> obtenerPorId(Integer id) {
        return centroRepository.findById(id);
    }

    public Centro guardar(Centro centro) {
        return centroRepository.save(centro);
    }

    public void eliminar(Integer id) {
        centroRepository.deleteById(id);
    }
}