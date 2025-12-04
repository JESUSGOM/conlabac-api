package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EmpleadosProveedores")
public class EmpleadoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpId")
    private Integer id;

    // Relación con el Proveedor (CIF + Centro)
    @Column(name = "EmpPrdCif", length = 9, nullable = false)
    private String cifProveedor;

    @Column(name = "EmpCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "EmpNif", length = 9)
    private String nif;

    @Column(name = "EmpNombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "EmpApellido1", length = 50, nullable = false)
    private String apellido1;

    @Column(name = "EmpApellido2", length = 50)
    private String apellido2;

    @Column(name = "EmpCargo", length = 100)
    private String cargo;

    @Column(name = "EmpFechaAcceso")
    private LocalDate fechaAcceso; // Validez inicio

    @Column(name = "EmpFechaFinAcceso")
    private LocalDate fechaFinAcceso; // Validez fin

    public EmpleadoProveedor() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCifProveedor() { return cifProveedor; }
    public void setCifProveedor(String cifProveedor) { this.cifProveedor = cifProveedor; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public LocalDate getFechaAcceso() { return fechaAcceso; }
    public void setFechaAcceso(LocalDate fechaAcceso) { this.fechaAcceso = fechaAcceso; }
    public LocalDate getFechaFinAcceso() { return fechaFinAcceso; }
    public void setFechaFinAcceso(LocalDate fechaFinAcceso) { this.fechaFinAcceso = fechaFinAcceso; }
}