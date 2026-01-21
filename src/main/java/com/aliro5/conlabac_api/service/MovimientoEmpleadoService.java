package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import com.aliro5.conlabac_api.repository.MovimientoEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoEmpleadoService {

    @Autowired
    private MovimientoEmpleadoRepository repo;

    public List<MovimientoEmpleado> listarActivos(Integer idCentro) {
        return repo.findByIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(idCentro);
    }

    // NUEVO MÉTODO: Para facilitar la búsqueda individual en controladores y tests
    public Optional<MovimientoEmpleado> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public MovimientoEmpleado registrarEntrada(String nif, String cif, Integer idCentro) {
        Optional<MovimientoEmpleado> yaDentro = repo.findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(nif, idCentro);
        if (yaDentro.isPresent()) {
            return yaDentro.get();
        }
        MovimientoEmpleado mov = new MovimientoEmpleado();
        mov.setNifEmpleado(nif);
        mov.setCifProveedor(cif);
        mov.setIdCentro(idCentro);
        mov.setFechaEntrada(LocalDateTime.now());
        return repo.save(mov);
    }

    public void registrarSalidaPorId(Integer id) {
        repo.findById(id).ifPresent(mov -> {
            mov.setFechaSalida(LocalDateTime.now());
            repo.save(mov);
        });
    }

    public void registrarSalida(String nif, Integer idCentro) {
        repo.findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(nif, idCentro)
                .ifPresent(mov -> {
                    mov.setFechaSalida(LocalDateTime.now());
                    repo.save(mov);
                });
    }
}