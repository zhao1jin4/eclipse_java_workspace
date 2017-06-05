package pdf_itext;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;

public class TestIText
{
	public static void writePDF() throws Exception
	{
		Document document = new Document();
		document.addTitle("title");
		document.addAuthor("Author");
		document.addKeywords("my ");
		document.addCreator("myCreator ");
		
		
		PdfWriter.getInstance(document, new FileOutputStream("d:/temp/HelloWorld.pdf"));
		document.open();
		
	   
		//--fail ֧������,Ҫ�Ӷ�Ӧ�汾 iTextAsian.jar,Ҫ���ذ�װ�����е�,��û��������Ƕ�뵽pdf�ļ�??????
//		BaseFont chineseFont = BaseFont.createFont("STSong-Light,Bold", "UniGB-UCS2-H", BaseFont.EMBEDDED);
//		Font font =  new Font(chineseFont);
		
		//--fail
//		FontSelector selector = new FontSelector();
//		//FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new BaseColor(255, 150, 200));
//		selector.addFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
//        selector.addFont(FontFactory.getFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
//        document.add(selector.process("hello ���ð�"));
		//---OK
		//����pdf��,ttf�ļ�������Ҳ����(������ϵͳ����)
		String ttf="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		Paragraph p1=new Paragraph("_Hello ���_",font);
		document.add(p1);
		document.newPage();   //��ҳ 
		
		Paragraph p2=new Paragraph(10,"_�������_",font);
		PdfPCell title = new PdfPCell(p2);
		title.setColspan(2);
		title.setBackgroundColor(BaseColor.RED);
		int numColumns = 2;
		PdfPTable table = new PdfPTable(numColumns);
		table.addCell(title);
		document.add(table);
		
		
		document.newPage();   //��ҳ   
		 //�����Ǿ���·����Ҳ������URL
		String strImg="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
		 Image img = Image.getInstance(strImg);   
		 // Image image = Image.getInstance(new URL(http://xxx.com/logo.jpg));   
		 img.setAbsolutePosition(0, 0);   
		 document.add(img);
		 
		 
		document.close();
		
		/* 
		//�����Web
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, buffer);
		//...
		response.setContentType("application/pdf");
		DataOutput output = new DataOutputStream(response.getOutputStream());
		byte[] bytes = buffer.toByteArray();
		response.setContentLength(bytes.length);
		output.write(bytes);
		*/
	}

	public static void readPDF() throws Exception
	{
		PdfReader reader = new PdfReader("D:/my/Spring_Դ�����.pdf");//���Ѿ�����PDF
		System.out.println(reader.getPdfVersion());
		
		//---��д
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("d:/temp/itext_out.pdf"));
		//����pdf��,ttf�ļ�������Ҳ����(������ϵͳ����)
		String ttf="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		for (int i=1; i<=reader.getNumberOfPages(); i++)
		{
			  //���pdfstamper�ڵ�ǰҳ���ϲ��ӡ���ݡ�Ҳ����˵ ��Щ���ݻḲ����ԭ�ȵ�pdf����֮�ϡ�
			  PdfContentByte over = stamper.getOverContent(i);
			  //��pdfreader��õ�ǰҳ�ֵ���󡣰����˸�ҳ��һЩ���ݡ������ҳ����������Ϣ��
			  PdfDictionary p = reader.getPageN(i);
			  //�õ�mediaBox ������Ÿ�ҳpdf�Ĵ�С��Ϣ��
			  PdfObject po =  p.get(new PdfName("MediaBox"));
			  System.out.println(po.isArray());
			  //po��һ�����������������˸�ҳpdf�������᷶Χ��
			  PdfArray pa = (PdfArray) po;
			  System.out.println(pa.size());
			  //����y������ֵ��
			  System.out.println(pa.getAsNumber(pa.size()-1));
			  //��ʼд���ı�
			  over.beginText();
			  //��������ʹ�С
			  over.setFontAndSize(font.getBaseFont(),10);
			  //������������λ��
			  over.setTextMatrix(107, 540);
			  //Ҫ�����text
			  over.showText("��Ҫ��[�ո�]���� " + i);
			  over.endText();
			  //����һ��image����
			  String strImg="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
			  Image image = Image.getInstance(strImg);
			  //����image��������λ��pa.getAsNumber(pa.size()-1)��floatValue() �Ǹ�ҳpdf�������y������ֵ
			  image.setAbsolutePosition(0,pa.getAsNumber(pa.size()-1).floatValue()-100);//0, 0, 841.92, 595.32
			  over.addImage(image);
			  //��һ��Ȧ��
			  over.setRGBColorStroke(0xFF, 0x00, 0x00);
			  over.setLineWidth(5f);
			  over.ellipse(250, 450, 350, 550);
			  over.stroke();

		}
		stamper.close();
		//---
		List list = SimpleBookmark.getBookmark ( reader ) ;
		for ( Iterator i = list.iterator () ; i.hasNext () ; ) 
		{
			showBookmark (( Map ) i.next ()) ;
		}
	}
	private static void showBookmark ( Map bookmark ) 
	{
		System.out.println ( bookmark.get ( "Title" )) ;
		ArrayList kids = ( ArrayList ) bookmark.get ( "Kids" ) ;
		if ( kids == null )
			return ;
		for ( Iterator i = kids.iterator () ; i.hasNext () ; ) 
		{
			showBookmark (( Map ) i.next ()) ;
		}
	}
	public static void main(String[] args) throws Exception
	{
		//writePDF();
		readPDF();
	}
}
