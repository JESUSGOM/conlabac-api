package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.repository.AperturaExtraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AperturaExtraService {

    @Autowired
    private AperturaExtraRepository repo;

    // Loggers para discriminación de sedes
    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public List<AperturaExtra> listar(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaDesc(idCentro);
    }

    public List<AperturaExtra> listarPorCentro(Integer idCentro) {
        return this.listar(idCentro);
    }

    public List<AperturaExtra> findByIdCentro(Integer idCentro) {
        return repo.findByIdCentroOrderByFechaDesc(idCentro);
    }

    public AperturaExtra guardar(AperturaExtra ae) {
        AperturaExtra guardado = repo.save(ae);

        // Registro de actividad en el log de la sede correspondiente
        registrarLogSede(guardado);

        return guardado;
    }

    /**
     * Escribe en el fichero de log de Tenerife o Gran Canaria según el centro
     */
    private void registrarLogSede(AperturaExtra ae) {
        String msg = "APERTURA EXTRA registrada - Fecha: " + ae.getFecha() + " - Motivo: " + ae.getMotivo();

        if (ae.getIdCentro() != null) {
            if (ae.getIdCentro() == 1) {
                logTF.info(msg);
            } else if (ae.getIdCentro() == 2) {
                logGC.info(msg);
            }
        }
    }

    public void eliminar(Integer id) {
        // Opcional: Registrar quién eliminó la apertura antes de borrar
        repo.deleteById(id);
    }

    public Optional<AperturaExtra> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
}