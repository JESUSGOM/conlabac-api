package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Se cambia el nombre para que coincida con las variables 'dni' y 'clavePlana' del modelo
    Optional<Usuario> findByDniAndClavePlana(String dni, String clavePlana);

    @Query("SELECT u FROM Usuario u WHERE u.idCentro = :idCentro")
    List<Usuario> findPersonalCentro(@Param("idCentro") int idCentro);
}