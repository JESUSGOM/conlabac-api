package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Contacto;
import com.aliro5.conlabac_api.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository repo;

    // Método principal de búsqueda
    public List<Contacto> listar(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    // CORRECCIÓN: Alias para resolver "cannot find symbol" en tests
    public List<Contacto> listarPorCentro(Integer idCentro) {
        return this.listar(idCentro);
    }

    // CORRECCIÓN: Alias solicitado por la línea 45 del test
    public List<Contacto> findByIdCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    public Contacto guardar(Contacto contacto) {
        return repo.save(contacto);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    public Optional<Contacto> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}