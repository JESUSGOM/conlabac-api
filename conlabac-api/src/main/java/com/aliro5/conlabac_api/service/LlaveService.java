package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LlaveService {

    @Autowired
    private LlaveRepository llaveRepository;

    public List<Llave> listarPorCentro(Integer idCentro) {
        return llaveRepository.findByIdCentro(idCentro);
    }
}