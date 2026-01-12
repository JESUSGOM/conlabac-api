package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KeyMoves")
public class KeyMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyOrden")
    private Integer id;

    // Código de la llave (Ej: K-101). En tu BD es un varchar(8).
    @Column(name = "KeyLlvOrden", length = 8, nullable = false)
    private String codigoLlave;

    @Column(name = "KeyCentro", nullable = false)
    private Integer idCentro;

    // --- DATOS DE ENTREGA (SALIDA) ---

    @Column(name = "KeyFechaEntrega", length = 8, nullable = false)
    private String fechaEntrega; // Formato: YYYYMMDD

    @Column(name = "KeyHoraEntrega", length = 6, nullable = false)
    private String horaEntrega; // Formato: HHMMSS

    @Column(name = "KeyNombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "KeyApellidoUno", length = 75, nullable = false)
    private String apellido1;

    @Column(name = "KeyApellidoDos", length = 75)
    private String apellido2;

    // --- DATOS DE DEVOLUCIÓN (ENTRADA) ---
    // Pueden ser nulos si la llave aún no ha sido devuelta

    @Column(name = "KeyFechaRecepcion", length = 8)
    private String fechaDevolucion;

    @Column(name = "KeyHoraRecepcion", length = 6)
    private String horaDevolucion;

    // --- CONSTRUCTOR VACÍO ---
    public KeyMove() {
    }

    // --- GETTERS Y SETTERS ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoLlave() {
        return codigoLlave;
    }

    public void setCodigoLlave(String codigoLlave) {
        this.codigoLlave = codigoLlave;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getHoraDevolucion() {
        return horaDevolucion;
    }

    public void setHoraDevolucion(String horaDevolucion) {
        this.horaDevolucion = horaDevolucion;
    }
}