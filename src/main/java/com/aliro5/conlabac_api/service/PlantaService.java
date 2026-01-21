package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Planta;
import com.aliro5.conlabac_api.repository.PlantaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository repo;

    public List<Planta> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    // Método necesario para el controlador
    public List<Planta> listarTodas() {
        return repo.findAll();
    }

    public Optional<Planta> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public Planta guardar(Planta planta) {
        return repo.save(planta);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}