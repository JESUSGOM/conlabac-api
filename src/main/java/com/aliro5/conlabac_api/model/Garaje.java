package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Garaje")
public class Garaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrjId")
    private Integer id;

    @Column(name = "GrjCentro")
    private Integer idCentro;

    @Column(name = "GrjFecha", length = 8, nullable = false)
    private String fechaTexto;

    @Column(name = "GrjFecha_dt")
    private LocalDateTime fechaEntrada;

    @Column(name = "GrjFechaSalida_dt")
    private LocalDateTime fechaSalida;

    @Column(name = "GrjNombre", length = 75, nullable = false)
    private String nombreConductor;

    @Column(name = "GrjEmpresa", length = 75, nullable = false)
    private String empresa;

    @Column(name = "GrjMarca", length = 25)
    private String marca;

    @Column(name = "GrjModelo", length = 25)
    private String modelo;

    @Column(name = "GrjColor", length = 25)
    private String color;

    @Column(name = "GrjMatricula", length = 9, nullable = false)
    private String matricula;

    public Garaje() {}

    // Getters y Setters (Se mantienen como los tenías)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getFechaTexto() { return fechaTexto; }
    public void setFechaTexto(String fechaTexto) { this.fechaTexto = fechaTexto; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
    public String getNombreConductor() { return nombreConductor; }
    public void setNombreConductor(String nombreConductor) { this.nombreConductor = nombreConductor; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}