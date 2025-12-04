package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Planta;
import com.aliro5.conlabac_api.repository.PlantaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository repo;

    // Listar por centro
    public List<Planta> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    // Guardar (Crear o Editar)
    public Planta guardar(Planta planta) {
        return repo.save(planta);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}