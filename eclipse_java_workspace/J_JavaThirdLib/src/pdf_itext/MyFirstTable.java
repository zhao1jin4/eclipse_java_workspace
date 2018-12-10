package pdf_itext;

 
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class MyFirstTable {
	
	// static String dirPrefix="D:/Program/all_code_workspace";
	static String dirPrefix="D:/NEW_/";
	
    public static void main(String[] args) throws  Exception 
    {
    	//Rectangle 可以通过读 pdf得到
        Document document = new Document(new Rectangle(800f,600f),10f,10f,30f,20f);//marginLeft,marginRigth,marginTop,marginBottom
        PdfWriter pdfWriter=  PdfWriter.getInstance(document, new FileOutputStream("d:/temp/first_table.pdf"));
        
        //--基于模板
//		String pdfFile =dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/template_user.pdf";
//		PdfReader reader = new PdfReader(pdfFile);
//		PdfImportedPage importedPage= pdfWriter.getImportedPage(reader, 1);
//      PdfContentByte content=pdfWriter.getDirectContentUnder();
//      content.addTemplate(importedPage, 0,0);
        
        //--
        document.open();
        document.add(createFirstTable());
        document.close();
    }
    public static PdfPTable createFirstTable() throws Exception  {
    	
    	String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.BOLD,new GrayColor(0.9f));
		
		
        PdfPTable table = new PdfPTable(3);//three columns
        table.setWidths(new float[] {0.3f,0.4f,0.3f});
        table.setWidthPercentage(80f);
        
        PdfPCell  cell = new PdfPCell(new Phrase("cell with colspan 3",font));
        cell.setColspan(3);
        
        cell.setUseAscender(true);
        cell.setMinimumHeight(20f);
        cell.setHorizontalAlignment(1);
        cell.setVerticalAlignment(5);
        cell.setNoWrap(false);
        cell.setBackgroundColor( BaseColor.GREEN);
        
        table.addCell(cell);
         
        cell = new PdfPCell(new Phrase("cell with rowspan 2",font));
        cell.setRowspan(2);
        table.addCell(cell);
        
        table.addCell(new Phrase("cn 中文 ,row 1; cell 1",font));
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
}
