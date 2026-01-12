package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Plantas")
public class Planta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PltId")
    private Integer id;

    @Column(name = "PltCentro")
    private Integer idCentro;

    @Column(name = "PltPlanta", length = 15)
    private String nombrePlanta;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getNombrePlanta() { return nombrePlanta; }
    public void setNombrePlanta(String nombrePlanta) { this.nombrePlanta = nombrePlanta; }
}