package apache_pdfbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfBoxTest {
	public static void read()  throws IOException
	{
		//依赖于commons-logging,fontbox
		boolean sort = false;
		String textFile = null;
		String pdfFile = "D:/my/Spring_源码分析.pdf";
		PDDocument document = PDDocument.load(new File(pdfFile));
		if (pdfFile.length() > 4) {
			textFile = pdfFile.substring(0, pdfFile.length() - 4) + ".txt";
		}
		// 文件输出流，写入文件到textFile
		Writer output = new OutputStreamWriter(new FileOutputStream(textFile),"UTF-8");
		// PDFTextStripper来提取文本
		PDFTextStripper stripper = new PDFTextStripper();//可加GBK,但中文OK
		stripper.setSortByPosition(sort);
		stripper.setStartPage(1);
		stripper.setEndPage(20);
		// 调用PDFTextStripper的writeText提取并输出文本
		stripper.writeText(document, output);
		output.close();
		document.close();
		
		
	}
	public static void read2()  throws IOException
	{
		String pdfFile = "D:/my/Spring_源码分析.pdf";
		PDFParser parser = new PDFParser(new RandomAccessFile(new File(pdfFile),"rw"));  
		parser.parse();  
		PDDocument pdfdocument = parser.getPDDocument();  
		PDFTextStripper stripper = new PDFTextStripper();  
		String result = stripper.getText(pdfdocument);  
		System.out.println(result);  
	}
	public static void write( )  throws IOException 
	{
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;//中文不行
		
		// Create a new font object by loading a TrueType font into the document
//		PDFont font = PDTrueTypeFont.loadTTF(document, "c:\\WINDOWS\\Fonts\\SIMHEI.TTF");//中文不正常
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.newLineAtOffset( 100, 700 ); 
		contentStream. showText( "Hello World_中__" );
		contentStream.endText();
		contentStream.close();
		document.save( "d:/temp/Hello World.pdf");
		document.close();
		
		
	}
	
	public static void main(String[] args)  throws Exception
	{
		//read();
		read2();
	//	write();
	}
}