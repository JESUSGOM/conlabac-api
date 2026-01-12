package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Proveedores")
@IdClass(ProveedorId.class) // Indicamos que usa clave compuesta
public class Proveedor {

    @Id
    @Column(name = "PrdCif", length = 9, nullable = false)
    private String cif;

    @Id
    @Column(name = "PrdCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "PrdDenominacion", nullable = false)
    private String denominacion;

    @Column(name = "PrdContacto")
    private String contacto;

    @Column(name = "PrdTelefono", length = 20)
    private String telefono;

    @Column(name = "PrdEmail")
    private String email;

    @Column(name = "PrdProvincia", length = 100)
    private String provincia;

    @Column(name = "PrdFechaAlta")
    private LocalDate fechaAlta;

    @Column(name = "PrdFechaExpiracion")
    private LocalDate fechaExpiracion;

    public Proveedor() {}

    // --- GETTERS Y SETTERS ---
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getDenominacion() { return denominacion; }
    public void setDenominacion(String denominacion) { this.denominacion = denominacion; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}