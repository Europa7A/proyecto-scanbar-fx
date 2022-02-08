package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.dventas.Consultar;
import servidor.dventas.ProductoDetalles;

public class ReportesPDF {

    public static void crearReporte(String _fecha, String _usuario, String _sesion) {

        Document _document;
        String _root_ruta;
        PdfWriter _pdf_write;
        HeaderFooterPageEvent _event;

        _document = new Document(PageSize.LETTER, 10f, 10f, 50f, 50f);
        _root_ruta = System.getProperty("user.home");

        try {

            _pdf_write = PdfWriter.getInstance(_document, new FileOutputStream(_root_ruta + "/Desktop/Reporte " + _usuario + " " + _fecha + ".pdf"));
            _event = new HeaderFooterPageEvent();

            _pdf_write.setPageEvent(_event);
            _document.open();
            tablaPdfDetallesVenta(_document, _fecha, _usuario, _sesion);
            tablPdfDetallesCaja(_document);

        } catch (DocumentException | FileNotFoundException e) {

        } finally {
            _document.close();
        }

    }

    private static void tablaPdfDetallesVenta(Document _document, String _fecha, String _usuario, String _sesion) {

        PdfPTable _table;
        Consultar _consultar_detalles_venta;
        ProductoDetalles[] _producto_detalles;
        Paragraph _titulo;
        String[] _cabecera = {"Nombre", "Total", "Fecha", "Hora", "Vendedor"};

        _table = new PdfPTable(5);
        _titulo = new Paragraph();

        try {

            _titulo.add("Detalles de ventas\n\n");
            _titulo.setAlignment(Paragraph.ALIGN_CENTER);
            _titulo.setFont(FontFactory.getFont("Arial", 14, BaseColor.BLACK));
            _document.add(_titulo);

            _table.addCell("Nombre");
            _table.addCell("Total");
            _table.addCell("Fecha");
            _table.addCell("Hora");
            _table.addCell("Vendedor");
            PdfPCell[] cells = _table.getRow(0).getCells();

            for (int i = 0; i < cells.length; i++) {
                cells[i].setBackgroundColor(WebColors.getRGBColor("#6A39D5"));

                Phrase _phr = new Phrase();
                _phr.setFont(FontFactory.getFont("Arial", 12, BaseColor.WHITE));
                _phr.add(_cabecera[i]);
                cells[i].setPhrase(_phr);

            }

            try {

                _consultar_detalles_venta = new Consultar("");
                _producto_detalles = _consultar_detalles_venta.consultarTodoPorFechaUsuarioYSesion(_fecha, _usuario, _sesion);

                if (_producto_detalles != null) {

                    for (ProductoDetalles _producto_detalle : _producto_detalles) {

                        _table.addCell(_producto_detalle.getNombre());
                        _table.addCell(_producto_detalle.getTotal());
                        _table.addCell(_producto_detalle.getFecha_registro());
                        _table.addCell(_producto_detalle.getHora_registro());
                        _table.addCell(_producto_detalle.getRegistrado_por());
                    }

                    _document.add(_table);
                    _document.add(new Paragraph("\n\n"));

                } else {

                }

            } catch (DocumentException e) {
            }

        } catch (DocumentException ex) {
            Logger.getLogger(ReportesPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void tablPdfDetallesCaja(Document _document) {

        PdfPTable _table;
        Paragraph _titulo;
        servidor.caja.Consultar _consultar_caja;
        String[] _cabecera = {"Usuario", "Total dinero", "Total ventas", "Descuadre", "Cierre de sesión"};

        _table = new PdfPTable(5);
        _titulo = new Paragraph();

        try {

            _titulo.add("\n\nDetalles de caja\n\n");
            _titulo.setAlignment(Paragraph.ALIGN_CENTER);
            _titulo.setFont(FontFactory.getFont("Arial", 14, BaseColor.BLACK));
            _document.add(_titulo);

            _table.addCell(_cabecera[0]);
            _table.addCell(_cabecera[1]);
            _table.addCell(_cabecera[2]);
            _table.addCell(_cabecera[3]);
            _table.addCell(_cabecera[4]);
            PdfPCell[] _cells1 = _table.getRow(0).getCells();

            for (int i = 0; i < _cells1.length; i++) {
                _cells1[i].setBackgroundColor(WebColors.getRGBColor("#6A39D5"));

                Phrase _phr = new Phrase();
                _phr.setFont(FontFactory.getFont("Arial", 12, BaseColor.WHITE));
                _phr.add(_cabecera[i]);
                _cells1[i].setPhrase(_phr);

            }

            try {

                _consultar_caja = new servidor.caja.Consultar("");
                _consultar_caja.consultarUltimo();

                if (_consultar_caja.productoEncontrado()) {

                    _table.addCell(_consultar_caja.getUsuario());
                    _table.addCell(_consultar_caja.getTotal_dinero());
                    _table.addCell(_consultar_caja.getTotal_ventas());
                    _table.addCell(_consultar_caja.getDescuadre());
                    _table.addCell(_consultar_caja.getFecha_fin_sesion() + " " + _consultar_caja.getHora_fin_sesion());

                    _document.add(_table);

                } else {
                    System.out.println("NADA");
                }

            } catch (DocumentException e) {
            }

        } catch (DocumentException ex) {
            Logger.getLogger(ReportesPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
