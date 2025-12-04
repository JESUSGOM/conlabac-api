package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Retposto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RptId")
    private Integer id;

    @Column(name = "RptCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "RptNombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "RptApellidoUno", length = 60, nullable = false)
    private String apellido1;

    @Column(name = "RptApellidoDos", length = 60, nullable = false)
    private String apellido2;

    @Column(name = "RptEmail", length = 50, nullable = false)
    private String email;

    public Contacto() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}