package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.repository.AperturaExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AperturaExtraService {

    @Autowired
    private AperturaExtraRepository repo;

    // Método original
    public List<AperturaExtra> listar(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaDesc(idCentro);
    }

    // CORRECCIÓN: Método que pide el test 'listarPorCentro'
    public List<AperturaExtra> listarPorCentro(Integer idCentro) {
        return this.listar(idCentro);
    }

    // CORRECCIÓN: Método que pide el test 'findByIdCentro'
    // Nota: Si el test espera una lista, usa la de arriba. Si espera uno solo, usa este:
    public List<AperturaExtra> findByIdCentro(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaDesc(idCentro);
    }

    public AperturaExtra guardar(AperturaExtra ae) {
        return repo.save(ae);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Recomendado para tests de integración
    public Optional<AperturaExtra> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}