package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.repository.AperturaExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AperturaExtraService {

    @Autowired
    private AperturaExtraRepository repo;

    public List<AperturaExtra> listar(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaDesc(idCentro);
    }

    public AperturaExtra guardar(AperturaExtra ae) {
        return repo.save(ae);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}