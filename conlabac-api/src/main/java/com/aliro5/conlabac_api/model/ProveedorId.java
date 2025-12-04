package com.aliro5.conlabac_api.model;

import java.io.Serializable;
import java.util.Objects;

public class ProveedorId implements Serializable {
    private String cif;
    private Integer idCentro;

    public ProveedorId() {
    }

    public ProveedorId(String cif, Integer idCentro) {
        this.cif = cif;
        this.idCentro = idCentro;
    }

    // hashCode y equals son OBLIGATORIOS para claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorId that = (ProveedorId) o;
        return Objects.equals(cif, that.cif) && Objects.equals(idCentro, that.idCentro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cif, idCentro);
    }
}