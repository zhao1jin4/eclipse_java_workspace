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
		//������commons-logging,fontbox
		boolean sort = false;
		String textFile = null;
		String pdfFile = "D:/my/Spring_Դ�����.pdf";
		PDDocument document = PDDocument.load(new File(pdfFile));
		if (pdfFile.length() > 4) {
			textFile = pdfFile.substring(0, pdfFile.length() - 4) + ".txt";
		}
		// �ļ��������д���ļ���textFile
		Writer output = new OutputStreamWriter(new FileOutputStream(textFile),"UTF-8");
		// PDFTextStripper����ȡ�ı�
		PDFTextStripper stripper = new PDFTextStripper();//�ɼ�GBK,������OK
		stripper.setSortByPosition(sort);
		stripper.setStartPage(1);
		stripper.setEndPage(20);
		// ����PDFTextStripper��writeText��ȡ������ı�
		stripper.writeText(document, output);
		output.close();
		document.close();
		
		
	}
	public static void read2()  throws IOException
	{
		String pdfFile = "D:/my/Spring_Դ�����.pdf";
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
		PDFont font = PDType1Font.HELVETICA_BOLD;//���Ĳ���
		
		// Create a new font object by loading a TrueType font into the document
//		PDFont font = PDTrueTypeFont.loadTTF(document, "c:\\WINDOWS\\Fonts\\SIMHEI.TTF");//���Ĳ�����
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.newLineAtOffset( 100, 700 ); 
		contentStream. showText( "Hello World_��__" );
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