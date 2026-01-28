package com.aliro5.conlabac_api.model.dto;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) para la solicitud de envío de emails.
 * Se utiliza para transferir los datos del formulario WEB hacia la API.
 */
public class EmailRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String destinatario;
    private String asunto;
    private String mensaje;
    private Integer idCentro;
    private String dniEmisor;

    // --- Constructor Vacío (Obligatorio para la deserialización JSON) ---
    public EmailRequestDTO() {
    }

    // --- Constructor con todos los campos (Útil para pruebas o instanciación rápida) ---
    public EmailRequestDTO(String destinatario, String asunto, String mensaje, Integer idCentro, String dniEmisor) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.idCentro = idCentro;
        this.dniEmisor = dniEmisor;
    }

    // --- Getters y Setters ---

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getDniEmisor() {
        return dniEmisor;
    }

    public void setDniEmisor(String dniEmisor) {
        this.dniEmisor = dniEmisor;
    }

    // Método toString para depuración (opcional pero recomendado)
    @Override
    public String toString() {
        return "EmailRequestDTO{" +
                "destinatario='" + destinatario + '\'' +
                ", asunto='" + asunto + '\'' +
                ", idCentro=" + idCentro +
                ", dniEmisor='" + dniEmisor + '\'' +
                '}';
    }
}