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

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class InformeMensualService {

    @Autowired private AperturaExtraRepository aperturaRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private IncidenciaRepository incidenciaRepo;
    @Autowired private MovimientoRepository movimientoRepo;

    private static final Color ROJO_CORP = new Color(218, 77, 98);
    private static final Font F_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font F_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final Font F_HEADER_BLANCO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    private static final Font F_SMALL = FontFactory.getFont(FontFactory.HELVETICA, 8);
    private static final Font F_TABLA_DATO = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public byte[] generarInforme(int mes, int anio, int idCentro) {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 110, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new MembreteEvento());
            document.open();

            String nombreMes = java.time.Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
            String fechaStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES")));

            generarPortada(document);
            document.newPage();
            generarPaginaDatos(document, idCentro, nombreMes, fechaStr);
            document.newPage();
            generarPaginaPersonal(document, idCentro);

            // Aperturas
            List<AperturaExtra> aperturas = aperturaRepo.findByCentroMesAnio(idCentro, mes, anio);
            if (aperturas != null && !aperturas.isEmpty()) {
                document.newPage();
                generarTablaAperturas(document, aperturas);
            }

            // INCIDENCIAS: Conversión de int a String para evitar "incompatible types"
            document.newPage();
            String mesStr = String.format("%02d", mes);
            String anioStr = String.valueOf(anio);
            List<Incidencia> incidencias = incidenciaRepo.findByCentroMesAnio(idCentro, mesStr, anioStr);
            generarTablaIncidencias(document, incidencias);

            document.newPage();
            generarPaginaEstadisticas(document, idCentro, mes, anio, nombreMes);

            document.close();
        } catch (Exception e) {
            if(document.isOpen()) document.close();
        }
        return out.toByteArray();
    }

    private void generarPortada(Document doc) throws DocumentException {
        Image img = cargarImagen("static/images/logoitcizq.png");
        if (img != null) {
            img.scaleToFit(400, 300);
            img.setAbsolutePosition((PageSize.A4.rotate().getWidth() - img.getScaledWidth()) / 2, 250);
            doc.add(img);
        }
        Paragraph p = new Paragraph("\n\n\n\n\n\n\n\n\n\n\nINFORME MENSUAL DE SERVICIO", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }

    private void generarPaginaDatos(Document doc, int idCentro, String nombreMes, String fechaStr) throws DocumentException {
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        addCellHeader(t, "CONTRATO:"); addCellHeader(t, "CENTRO:");
        addCellData(t, (idCentro == 1 ? "SC 0079/2025" : "SC 0870/2024"));
        addCellData(t, (idCentro == 1 ? "Tenerife" : "Las Palmas"));
        doc.add(t);
    }

    private void generarPaginaPersonal(Document doc, int idCentro) throws DocumentException {
        Paragraph p = new Paragraph("PERSONAL ADSCRITO AL SERVICIO", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p); doc.add(new Paragraph("\n"));
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        addCellHeaderBlanco(t, "NOMBRE Y APELLIDOS"); addCellHeaderBlanco(t, "PUESTO");
        List<Usuario> personal = usuarioRepo.findPersonalCentro(idCentro);
        for(Usuario u : personal) {
            addCellData(t, u.getNombre() + " " + u.getApellido1());
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
        addCellHeaderBlanco(t, "FECHA"); addCellHeaderBlanco(t, "INICIO"); addCellHeaderBlanco(t, "FIN"); addCellHeaderBlanco(t, "MOTIVO");
        for(AperturaExtra ae : lista) {
            addCellDataCenter(t, ae.getFecha().toString());
            addCellDataCenter(t, ae.getHoraInicio().toString());
            addCellDataCenter(t, ae.getHoraFinal().toString());
            addCellData(t, ae.getMotivo());
        }
        doc.add(t);
    }

    private void generarTablaIncidencias(Document doc, List<Incidencia> lista) throws DocumentException {
        Paragraph p = new Paragraph("RELACIÓN DE INCIDENCIAS", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p); doc.add(new Paragraph("\n"));
        PdfPTable t = new PdfPTable(4);
        t.setWidthPercentage(100);
        addCellHeaderBlanco(t, "FECHA"); addCellHeaderBlanco(t, "HORA"); addCellHeaderBlanco(t, "AVISO"); addCellHeaderBlanco(t, "DESCRIPCIÓN");
        for(Incidencia i : lista) {
            addCellDataCenter(t, i.getFecha()); addCellDataCenter(t, i.getHora());
            addCellData(t, i.getComunicadoA()); addCellDataSmall(t, i.getTexto());
        }
        doc.add(t);
    }

    private void generarPaginaEstadisticas(Document doc, int idCentro, int mes, int anio, String nombreMes) throws DocumentException {
        Integer total = movimientoRepo.contarVisitasMes(idCentro, mes, anio);
        Paragraph p = new Paragraph("ESTADÍSTICAS DE VISITAS - " + nombreMes + " (Total: " + (total != null ? total : 0) + ")", F_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }

    private Image cargarImagen(String path) {
        try { return Image.getInstance(new ClassPathResource(path).getURL()); } catch (Exception e) { return null; }
    }
    private void addCellHeader(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt, F_BOLD)); c.setBackgroundColor(Color.LIGHT_GRAY); t.addCell(c);
    }
    private void addCellHeaderBlanco(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt, F_HEADER_BLANCO)); c.setBackgroundColor(ROJO_CORP); c.setHorizontalAlignment(Element.ALIGN_CENTER); t.addCell(c);
    }
    private void addCellData(PdfPTable t, String txt) { t.addCell(new Phrase(txt != null ? txt : "", F_TABLA_DATO)); }
    private void addCellDataCenter(PdfPTable t, String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt != null ? txt : "", F_TABLA_DATO)); c.setHorizontalAlignment(Element.ALIGN_CENTER); t.addCell(c);
    }
    private void addCellDataSmall(PdfPTable t, String txt) { t.addCell(new Phrase(txt != null ? txt : "", F_SMALL)); }

    class MembreteEvento extends PdfPageEventHelper {
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                Image logo = cargarImagen("static/images/logoitcizq.png");
                if (logo != null) {
                    logo.scaleToFit(80, 30);
                    logo.setAbsolutePosition(30, PageSize.A4.rotate().getHeight() - 50);
                    writer.getDirectContent().addImage(logo);
                }
            } catch (Exception e) {}
        }
    }
}