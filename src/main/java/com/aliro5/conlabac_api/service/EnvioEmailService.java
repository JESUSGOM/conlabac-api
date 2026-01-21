package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnvioEmailService {

    @Autowired
    private EnvioEmailRepository repo;

    public List<EnvioEmail> listarTodo() {
        return repo.findAll();
    }

    public List<EnvioEmail> listarPorDestinatario(String email) {
        return repo.findByDestinatarioOrderByFechaHoraDtDesc(email);
    }

    public EnvioEmail guardarLog(EnvioEmail log) {
        return repo.save(log);
    }
}