package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository repo;

    // Listar por centro
    public List<Alquiler> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    // Guardar (Crear o Editar)
    public Alquiler guardar(Alquiler alquiler) {
        return repo.save(alquiler);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}