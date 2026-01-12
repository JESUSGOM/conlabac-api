package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Llaves")
public class Llave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LlvOrden")
    private Integer id;

    @Column(name = "LlvCodigo", length = 8, nullable = false)
    private String codigo; // El código grabado en la llave (ej: K-101)

    @Column(name = "LlvCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "LlvPuerta", length = 50, nullable = false)
    private String puerta; // Qué puerta abre

    @Column(name = "LlvPlanta", length = 22, nullable = false)
    private String planta;

    @Column(name = "LlvCajetin", nullable = false)
    private Integer cajetin; // Número de posición en el armario de llaves

    @Column(name = "LlvRestriccion", length = 200)
    private String restriccion; // Texto de aviso (ej: "Solo Personal Autorizado")

    // --- Constructor Vacío (Obligatorio para JPA) ---
    public Llave() {
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public Integer getCajetin() {
        return cajetin;
    }

    public void setCajetin(Integer cajetin) {
        this.cajetin = cajetin;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }
}