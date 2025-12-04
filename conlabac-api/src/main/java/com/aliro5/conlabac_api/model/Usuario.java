package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @Column(name = "UsuDni", length = 9, nullable = false)
    private String dni;

    @Column(name = "UsuClave", length = 9)
    private String clavePlana; // Claves antiguas

    @Column(name = "UsuClaveBcrypt", length = 60)
    private String claveBcrypt; // Claves seguras

    // --- NUEVO: CAMPO TEMPORAL PARA RECIBIR LA CLAVE DE LA WEB ---
    @Transient // Importante: No se guarda en BD, solo sirve de transporte
    private String clave;

    @Column(name = "UsuNombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "UsuApellidoUno", length = 60, nullable = false)
    private String apellido1;

    @Column(name = "UsuApellidoDos", length = 60)
    private String apellido2;

    @Column(name = "UsuCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "UsuTipo", length = 1, nullable = false)
    private String tipo;

    public Usuario() {
    }

    // --- GETTERS Y SETTERS ---
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getClavePlana() { return clavePlana; }
    public void setClavePlana(String clavePlana) { this.clavePlana = clavePlana; }

    public String getClaveBcrypt() { return claveBcrypt; }
    public void setClaveBcrypt(String claveBcrypt) { this.claveBcrypt = claveBcrypt; }

    // Getter y Setter para el nuevo campo temporal
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
