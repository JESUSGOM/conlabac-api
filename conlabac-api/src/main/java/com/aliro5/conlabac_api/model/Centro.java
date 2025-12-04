package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Centros")
public class Centro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CenId")
    private Integer id;

    @Column(name = "CenDen", length = 100, nullable = false)
    private String denominacion;

    @Column(name = "CenDireccion", length = 30, nullable = false)
    private String direccion;

    @Column(name = "CenCodigoPostal", nullable = false)
    private Integer codigoPostal;

    @Column(name = "CenProvincia", length = 30, nullable = false)
    private String provincia;

    @Column(name = "CenTelefono", length = 15, nullable = false)
    private String telefono;

    @Column(name = "CenFax", length = 15)
    private String fax;

    // --- Constructor Vacío (Obligatorio para JPA) ---
    public Centro() {
    }

    // --- Constructor con todos los campos (Opcional, útil para crear objetos) ---
    public Centro(Integer id, String denominacion, String direccion, Integer codigoPostal, String provincia, String telefono, String fax) {
        this.id = id;
        this.denominacion = denominacion;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.telefono = telefono;
        this.fax = fax;
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    // Opcional: toString para depuración
    @Override
    public String toString() {
        return "Centro [id=" + id + ", denominacion=" + denominacion + ", provincia=" + provincia + "]";
    }
}