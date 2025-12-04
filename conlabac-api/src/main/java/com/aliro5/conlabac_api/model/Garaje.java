package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Garaje")
public class Garaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrjId")
    private Integer id;

    // Fecha en formato texto antiguo (YYYYMMDD) - Para compatibilidad
    @Column(name = "GrjFecha", length = 8, nullable = false)
    private String fechaTexto;

    // Fecha en formato moderno (Date) - Para ordenar y filtrar
    @Column(name = "GrjFecha_dt")
    private LocalDate fecha;

    @Column(name = "GrjNombre", length = 75, nullable = false)
    private String nombreConductor;

    @Column(name = "GrjEmpresa", length = 75, nullable = false)
    private String empresa;

    @Column(name = "GrjMarca", length = 25, nullable = false)
    private String marca;

    @Column(name = "GrjModelo", length = 25, nullable = false)
    private String modelo;

    @Column(name = "GrjColor", length = 25, nullable = false)
    private String color;

    @Column(name = "GrjMatricula", length = 9, nullable = false)
    private String matricula;

    // --- Constructor Vacío (Obligatorio para JPA) ---
    public Garaje() {
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaTexto() {
        return fechaTexto;
    }

    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}