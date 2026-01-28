package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {

    @Autowired private LlaveRepository llaveRepository;

    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public List<Llave> listarPorCentro(Integer idCentro) {
        return llaveRepository.findByIdCentro(idCentro);
    }

    public List<Llave> listarDisponibles(Integer idCentro) {
        return llaveRepository.findDisponiblesPorCentro(idCentro);
    }

    public Llave obtenerPorCodigo(String codigo) {
        return llaveRepository.findByCodigo(codigo).orElse(null);
    }

    public Llave guardar(Llave llave) {
        Llave guardada = llaveRepository.save(llave);

        // LOG DE SEDE (Inventario/Edición)
        String msg = "LLAVE ACTUALIZADA/CREADA: " + llave.getCodigo() + " - " + llave.getPuerta();
        if (llave.getIdCentro() == 1) logTF.info(msg);
        else if (llave.getIdCentro() == 2) logGC.info(msg);

        return guardada;
    }

    public Optional<Llave> obtenerPorId(Integer id) {
        return llaveRepository.findById(id);
    }

    public void eliminar(Integer id) {
        obtenerPorId(id).ifPresent(ll -> {
            String msg = "LLAVE ELIMINADA del inventario: " + ll.getCodigo();
            if (ll.getIdCentro() == 1) logTF.info(msg);
            else if (ll.getIdCentro() == 2) logGC.info(msg);
            llaveRepository.deleteById(id);
        });
    }
}