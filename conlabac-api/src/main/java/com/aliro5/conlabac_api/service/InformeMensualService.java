package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.*;
import com.aliro5.conlabac_api.repository.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class InformeMensualService {

    @Autowired private AperturaExtraRepository aperturaRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private IncidenciaRepository incidenciaRepo;
    @Autowired private MovimientoRepository movimientoRepo;

    // --- CORRECCIÓN 1: F_TABLA_DATO debe ser un Font, no un String ---
    private static final Font F_TABLA_DATO = FontFactory.getFont(FontFactory.HELVETICA, 10);

    // COLORES
    private static final Color ROJO_CORP = new Color(218, 77, 98);

    // FUENTES
    private static final Font F_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font F_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private static final Font F_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final Font F_HEADER_BLANCO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    private static final Font F_SMALL = FontFactory.getFont(FontFactory.HELVETICA, 8);

    public byte[] generarInforme(int mes, int anio, int idCentro) {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 110, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);

            // Evento Cabecera/Pie
            MembreteEvento event = new MembreteEvento();
            writer.setPageEvent(event);

            document.open();

            String nombreMes = java.time.Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
            LocalDate fechaRef = LocalDate.of(anio, mes, 1).plusMonths(1).minusDays(1);
            String fechaStr = fechaRef.getDayOfMonth() + " de " +
                    java.time.Month.of(fechaRef.getMonthValue()).getDisplayName(TextStyle.FULL, new Locale("es", "ES")) +
                    " de " + anio;

            // --- PÁGINA 1: PORTADA ---
            generarPortada(document);

            // --- PÁGINA 2: DATOS Y DESCRIPCIÓN ---
            document.newPage();
            generarPaginaDatos(document, idCentro, nombreMes, fechaStr);

            // --- PÁGINA 3: PERSONAL ---
            document.newPage();
            generarPaginaPersonal(document, idCentro);

            // --- PÁGINA 4: APERTURAS EXTRA ---
            List<AperturaExtra> aperturas = aperturaRepo.findByCentroMesAnio(idCentro, mes, anio);
            if (!aperturas.isEmpty()) {
                document.newPage();
                generarTablaAperturas(document, aperturas);
            }

            // --- PÁGINA 5: INCIDENCIAS ---
            document.newPage();
            List<Incidencia> incidencias = incidenciaRepo.findByCentroMesAnio(idCentro, mes, anio);
            generarTablaIncidencias(document, incidencias);

            // --- PÁGINA 6: ESTADÍSTICAS ---
            document.newPage();
            generarPaginaEstadisticas(document, idCentro, mes, anio, nombreMes);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Aseguramos cierre en caso de error para ver algo del PDF si es posible
            if(document.isOpen()) {
                document.close();
            }
        }

        return out.toByteArray();
    }

    // ===================== SECCIONES DEL INFORME =====================

    private void generarPortada(Document doc) throws DocumentException {
        try {
            Image img = cargarImagen("static/img/Imagen1.jpg");
            if (img != null) {
                img.scaleToFit(750, 500);
                img.setAbsolutePosition(
                        (PageSize.A4.rotate().getWidth() - img.getScaledWidth()) / 2,
                        (PageSize.A4.rotate().getHeight() - img.getScaledHeight()) / 2 - 20
                );
                doc.add(img);
            } else {
                doc.add(new Paragraph("\n\nERROR: Imagen portada no encontrada", F_TITULO));
            }
            doc.add(new Paragraph(" "));
        } catch (Exception e) {}
    }

    private void generarPaginaDatos(Document doc, int idCentro, String nombreMes, String fechaStr) throws DocumentException {
        String contrato = (idCentro == 1) ? "SC 0079/2025" : "SC 0870/2024";
        String direccion = (idCentro == 1) ? "Plaza Sixto Machado, 3. 38009-Santa Cruz de Tenerife" : "Calle Cebrián, 3. 35003-Las Palmas de Gran Canaria";
        String contacto = (idCentro == 1) ? "María Carmen Betancor Reula" : "Adriana Domínguez Sicilia";

        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        t.setWidths(new float[]{1, 1});

        addCellHeader(t, "CLIENTE:");    addCellHeader(t, "CIF");
        addCellData(t, contrato);        addCellData(t, "A35313170");
        addCellHeader(t, "DIRECCIÓN:");  addCellHeader(t, "PERSONA DE CONTACTO");
        addCellData(t, direccion);       addCellData(t, contacto);
        addCellHeader(t, "FECHA:");      addCellData(t, "");
        addCellData(t, fechaStr);        addCellData(t, "");

        doc.add(t);

        Paragraph pTit = new Paragraph("\nINFORME MENSUAL DEL MES " + nombreMes, F_TITULO);
        pTit.setAlignment(Element.ALIGN_CENTER);
        doc.add(pTit);
        doc.add(new Paragraph("______________________________________________________________________________\n\n"));

        String centroTxt = (idCentro == 1) ? "insular de Tenerife" : "insular de Las Palmas de Gran Canaria";
        String texto = "El servicio de auxiliares de recepción para el centro " + centroTxt +
                ", del Instituto Tecnológico de Canarias, S.A., a partir de ahora: 'ITC' engloba la siguiente actividad:\n\n" +
                "Se trata de un edificio que alberga las oficinas principales del ITC... (Texto completo omitido)...";

        Paragraph pDesc = new Paragraph(texto, F_NORMAL);
        pDesc.setAlignment(Element.ALIGN_JUSTIFIED);
        doc.add(pDesc);

        doc.add(new Paragraph("\n"));

        PdfPTable tHor = new PdfPTable(2);
        tHor.setWidthPercentage(60);
        tHor.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell cHead = new PdfPCell(new Phrase("HORARIO DEL SERVICIO", F_BOLD));
        cHead.setColspan(2); cHead.setBorder(0); cHead.setHorizontalAlignment(Element.ALIGN_CENTER);
        tHor.addCell(cHead);

        addCellHeader(tHor, "MAÑANAS"); addCellHeader(tHor, "TARDES");
        addCellDataCenter(tHor, "6:45 - 12:00"); addCellDataCenter(tHor, "12:00 - 17:15");
        addCellHeader(tHor, "APERTURA:"); addCellHeader(tHor, "CIERRE:");
        addCellDataCenter(tHor, "6:45");  addCellDataCenter(tHor, "17:15");

        doc.add(tHor);
    }

    private void generarPaginaPersonal(Document doc, int idCentro) throws DocumentException {
        Paragraph p = new Paragraph("PERSONAL ADSCRITO AL SERVICIO", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p); doc.add(new Paragraph("\n"));

        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        addCellHeader(t, "APELLIDOS Y NOMBRE");
        addCellHeader(t, "PUESTO / CARGO");

        List<Usuario> personal = usuarioRepo.findPersonalCentro(idCentro);
        for(Usuario u : personal) {
            addCellData(t, u.getApellido1() + " " + (u.getApellido2()!=null?u.getApellido2():"") + ", " + u.getNombre());
            addCellDataCenter(t, "Auxiliar de Servicios");
        }
        doc.add(t);
    }

    private void generarTablaAperturas(Document doc, List<AperturaExtra> lista) throws DocumentException {
        Paragraph p = new Paragraph("RELACIÓN DE APERTURAS EXTRAORDINARIAS", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p); doc.add(new Paragraph("\n"));

        PdfPTable t = new PdfPTable(4);
        t.setWidthPercentage(100);
        t.setWidths(new float[]{2, 2, 2, 6});

        addCellHeaderBlanco(t, "FECHA");
        addCellHeaderBlanco(t, "HORA INICIO");
        addCellHeaderBlanco(t, "HORA FINAL");
        addCellHeaderBlanco(t, "SERVICIO PRESTADO");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(AperturaExtra ae : lista) {
            addCellDataCenter(t, ae.getFecha().format(fmt));
            addCellDataCenter(t, ae.getHoraInicio().toString());
            addCellDataCenter(t, ae.getHoraFinal().toString());
            addCellData(t, ae.getMotivo());
        }
        doc.add(t);
    }

    private void generarTablaIncidencias(Document doc, List<Incidencia> lista) throws DocumentException {
        Paragraph p = new Paragraph("RELACIÓN DE INCIDENCIAS DEL SERVICIO", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p); doc.add(new Paragraph("\n"));

        PdfPTable t = new PdfPTable(6);
        t.setWidthPercentage(100);
        t.setWidths(new float[]{1.5f, 1.2f, 2.5f, 1.5f, 2.5f, 5.0f});

        addCellHeaderBlanco(t, "FECHA"); addCellHeaderBlanco(t, "HORA");
        addCellHeaderBlanco(t, "COMUNICADO A"); addCellHeaderBlanco(t, "FORMA");
        addCellHeaderBlanco(t, "POR"); addCellHeaderBlanco(t, "INCIDENCIA");

        for(Incidencia i : lista) {
            String f = i.getFecha();
            if(f != null && f.length() == 8) f = f.substring(6,8)+"/"+f.substring(4,6)+"/"+f.substring(0,4);
            String h = i.getHora();
            if(h != null && h.length() == 6) h = h.substring(0,2)+":"+h.substring(2,4);

            addCellDataCenter(t, f);
            addCellDataCenter(t, h);
            addCellDataSmall(t, i.getComunicadoA());
            addCellDataCenter(t, i.getModoComunica());
            addCellDataSmall(t, i.getUsuario());
            addCellDataSmall(t, i.getTexto());
        }
        doc.add(t);
    }

    private void generarPaginaEstadisticas(Document doc, int idCentro, int mes, int anio, String nombreMes) throws DocumentException {
        Integer total = movimientoRepo.contarVisitasMes(idCentro, mes, anio);
        if(total == null) total = 0;

        PdfPTable tHead = new PdfPTable(1);
        tHead.setWidthPercentage(100);
        PdfPCell cTotal = new PdfPCell(new Phrase("Total Visitas recibidas en el mes de " + nombreMes + " es de " + total, F_TITULO));
        cTotal.setBackgroundColor(Color.WHITE);
        cTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
        cTotal.setPadding(10);
        tHead.addCell(cTotal);
        doc.add(tHead); doc.add(new Paragraph("\n"));

        if(total > 0) {
            PdfPTable t = new PdfPTable(3);
            t.setWidthPercentage(100);
            t.setWidths(new float[]{6, 2, 2});

            addCellHeaderBlanco(t, "VISITAN A:");
            addCellHeaderBlanco(t, "VECES:");
            addCellHeaderBlanco(t, "PORCENTAJE:");

            List<Object[]> stats = movimientoRepo.obtenerEstadisticasMes(idCentro, mes, anio);
            DecimalFormat df = new DecimalFormat("0.00");

            for(Object[] row : stats) {
                String destino = (String) row[0];
                Long count = ((Number) row[1]).longValue();
                double pct = (count * 100.0) / total;

                addCellData(t, destino);
                addCellDataCenter(t, String.valueOf(count));
                addCellDataCenter(t, df.format(pct) + "%");
            }
            doc.add(t);
        }
    }

    // ===================== UTILIDADES =====================
    private Image cargarImagen(String path) {
        try {
            return Image.getInstance(new ClassPathResource(path).getURL());
        } catch (Exception e) {
            return null;
        }
    }
    private void addCellHeader(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt, F_BOLD));
        c.setBackgroundColor(ROJO_CORP); c.setPadding(5); t.addCell(c);
    }
    private void addCellHeaderBlanco(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt, F_HEADER_BLANCO));
        c.setBackgroundColor(ROJO_CORP); c.setHorizontalAlignment(Element.ALIGN_CENTER); c.setPadding(5); t.addCell(c);
    }

    // --- CORRECCIÓN 2: Eliminado Float.parseFloat() que rompía el PDF al leer texto ---
    private void addCellData(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt != null ? txt : "", F_TABLA_DATO));
        c.setPadding(4); t.addCell(c);
    }
    // --- CORRECCIÓN 2b: Igual aquí ---
    private void addCellDataCenter(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt != null ? txt : "", F_TABLA_DATO));
        c.setHorizontalAlignment(Element.ALIGN_CENTER); c.setPadding(4); t.addCell(c);
    }

    private void addCellDataSmall(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt != null ? txt : "", F_SMALL));
        c.setPadding(3); t.addCell(c);
    }

    class MembreteEvento extends PdfPageEventHelper {
        PdfTemplate totalPages;
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            totalPages = writer.getDirectContent().createTemplate(30, 16);
        }
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            if (writer.getPageNumber() == 1) return;
            try {
                PdfContentByte cb = writer.getDirectContent();
                Image logo1 = cargarImagen("static/img/logoitcizq.png");
                if (logo1 != null) { logo1.scaleToFit(100, 40); logo1.setAbsolutePosition(30, 540); cb.addImage(logo1); }
                Image logo2 = cargarImagen("static/img/Envera_Logo_79_30.png");
                if (logo2 != null) { logo2.scaleToFit(100, 40); logo2.setAbsolutePosition(700, 540); cb.addImage(logo2); }
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Servicio de Recepción", F_TITULO), 421, 555, 0);
            } catch (Exception e) { }
        }
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            if (writer.getPageNumber() == 1) return;
            PdfContentByte cb = writer.getDirectContent();
            String text = "Pag " + writer.getPageNumber() + " / ";
            float len = F_SMALL.getBaseFont().getWidthPoint(text, 8);
            cb.beginText();
            cb.setFontAndSize(F_SMALL.getBaseFont(), 8);
            cb.setTextMatrix(420, 20);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(totalPages, 420 + len, 20);
        }
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(totalPages, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1), F_SMALL), 0, 0, 0);
        }
    }
}