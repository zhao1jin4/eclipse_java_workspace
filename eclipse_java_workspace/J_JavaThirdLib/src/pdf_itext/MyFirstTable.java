package pdf_itext;


import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MyFirstTable {
    public static void main(String[] args) throws DocumentException, IOException   
    {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("d:/temp/first_table.pdf"));
        document.open();
        document.add(createFirstTable());
        document.close();
    }
    public static PdfPTable createFirstTable() {
        PdfPTable table = new PdfPTable(3);//three columns
        
        PdfPCell  cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
}
