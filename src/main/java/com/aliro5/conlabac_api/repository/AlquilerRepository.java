package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {

    // Si usas el nombre estándar de Spring Data JPA:
    List<Alquiler> findByIdCentro(Integer idCentro);

    // O si prefieres asegurar el mapeo con la columna física de la BD:
    @Query("SELECT a FROM Alquiler a WHERE a.idCentro = :idCentro")
    List<Alquiler> buscarPorCentro(@Param("idCentro") Integer idCentro);
}