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

    public List<Contacto> listar(Integer idCentro) {
        return repo.findByIdCentroOrderByNombreAsc(idCentro);
    }

    public Contacto guardar(Contacto contacto) {
        return repo.save(contacto);
    }

    public Optional<Contacto> obtener(Integer id) {
        return repo.findById(id);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}