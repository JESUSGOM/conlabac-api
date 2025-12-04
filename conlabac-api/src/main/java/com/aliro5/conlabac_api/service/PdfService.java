package com.aliro5.conlabac_api.service;
import com.aliro5.conlabac_api.model.Movimiento;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generarReporteMovimientos(List<Movimiento> lista, String titulo) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph paraTitulo = new Paragraph(titulo, fontTitulo);
            paraTitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(paraTitulo);
            document.add(new Paragraph(" ")); // Espacio

            // 2. Tabla
            PdfPTable table = new PdfPTable(5); // 5 Columnas
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 2, 2}); // Anchos relativos

            // Cabecera
            addTableHeader(table, "ID");
            addTableHeader(table, "Visitante");
            addTableHeader(table, "Empresa/Procedencia");
            addTableHeader(table, "Entrada");
            addTableHeader(table, "Salida");

            // Datos
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm dd/MM");

            for (Movimiento m : lista) {
                table.addCell(String.valueOf(m.getId()));
                table.addCell(m.getNombre() + " " + m.getApellido1());
                table.addCell(m.getProcedencia() != null ? m.getProcedencia() : "-");

                String entrada = m.getFechaEntrada() != null ? m.getFechaEntrada().format(fmt) : "";
                String salida = m.getFechaSalida() != null ? m.getFechaSalida().format(fmt) : "";

                table.addCell(entrada);
                table.addCell(salida);
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table, String title) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        header.setPhrase(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
}