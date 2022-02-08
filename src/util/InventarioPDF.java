package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import servidor.producto.Consultar;

public class InventarioPDF {

    public static void crearReporte() {

        servidor.producto.Producto[] _producto;
        Consultar _consultar_produtos;
        String _user_root;
        PdfPTable _table;
        Document _document;
        PdfWriter _pdf_writer;
        Paragraph _parrafo;
        HeaderFooterPageEvent _event;
        PdfPCell[] _cells;
        final String _cabecera[] = {"ID", "Nombre", "Precio", "Cantidad", "Fecha registro"};

        _consultar_produtos = new Consultar("");
        _producto = _consultar_produtos.consultarTodo();
        _user_root = System.getProperty("user.home");
        _table = new PdfPTable(5);
        _document = new Document(PageSize.LETTER, 10f, 10f, 50f, 50f);
        _parrafo = new Paragraph();
        _event = new HeaderFooterPageEvent();

        try {

            _pdf_writer = PdfWriter.getInstance(_document, new FileOutputStream(_user_root + "/Desktop/Inventario " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf"));
            _pdf_writer.setPageEvent(_event);

            _document.open();
            
            _parrafo.add("Inventario de productos\n\n");
            _parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            _parrafo.setFont(FontFactory.getFont("Arial", 14, BaseColor.BLACK));
            _document.add(_parrafo);

            for (String string : _cabecera) {
                _table.addCell(string);
            }

            if (_producto != null) {
                for (servidor.producto.Producto producto : _producto) {
                    _table.addCell(producto.getID());
                    _table.addCell(producto.getNombre());
                    _table.addCell(producto.getPrecio());
                    _table.addCell(producto.getCantidad());
                    _table.addCell(producto.getFechaRegistro());
                }
            }
            
            
            _cells = _table.getRow(0).getCells();
            
            for (PdfPCell _cell : _cells) {
                _cell.setPadding(5f);
                _cell.setBackgroundColor(WebColors.getRGBColor("#6A39D5"));
            }
            
            
            for (int i = 0; i < _cells.length; i++) {
                Phrase _phr = new Phrase();
                _phr.setFont(FontFactory.getFont("Arial", 12, BaseColor.WHITE));
                _phr.add(_cabecera[i]);
                _cells[i].setPhrase(_phr);

            }
            
            
            
            
            
           
            
            _document.add(_table);

        } catch (DocumentException | FileNotFoundException e) {
        } finally {
            _document.close();
        }

    }

}
