package pdf_itext;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
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
	// static String dirPrefix="D:/Program/all_code_workspace";
	static String dirPrefix="D:/NEW_/";
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
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		Paragraph p1=new Paragraph("_Hello ���_",font);
		p1.setIndentationLeft(10f);
		p1.setIndentationRight(10f);
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
		String strImg=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
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
		String pdfFile = "D:\\book\\��\\�����Spring Cloud�������飨һ��.pdf";
	
		PdfReader reader = new PdfReader(pdfFile);//���Ѿ�����PDF
		System.out.println(reader.getPdfVersion());
		
		//---��д
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("d:/temp/itext_out.pdf"));
		//����pdf��,ttf�ļ�������Ҳ����(������ϵͳ����)
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
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
			  String strImg=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
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
		List<HashMap<String, Object>> list = SimpleBookmark.getBookmark ( reader ) ;
		for ( Iterator<HashMap<String, Object>> i = list.iterator () ; i.hasNext () ; ) 
		{
			showBookmark (i.next ()) ;
		}
	}
	private static void showBookmark ( Map<String, Object> bookmark ) 
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
	public static void fillPdfTemplateVariable ( ) throws Exception 
	{  
//		world ����ģ���ļ� ���Ϊ(libreOffice����) pdf  -> Adobe Acrobat Reader Proc DC  �ٱ༭ PDF 
//		->׼����->�������� ���"�ı�"�� ,�ϳ�һ������, ���ñ���,itext����Ϳ��Ը�ֵ 
		
		Map<String,String> param =new HashMap<>();
		param.put("my_username", "����");
		param.put("my_age", "20");
		
		String pdfFile =dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/template_user.pdf";
		PdfReader reader = new PdfReader(pdfFile);//���Ѿ�����PDF
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:/temp/template_user_res.pdf"));
		stamper.getUnderContent(1);
		 
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		
		//����ģ�����
		AcroFields form=stamper.getAcroFields();
		form.addSubstitutionFont(chineseFont);
		for(String key :param.keySet())
		{
			form.setField(key, param.get(key));
		}
		
		
		stamper.close();
		reader.close();
		
	}
	public static void mergePdfFile () throws Exception 
	{
		String[] files=new String[] {"d:/temp/template_user_res.pdf","d:/temp/HelloWorld.pdf"};
		String res="d:/temp/merge_res.pdf";
		PdfReader pdfReader=new PdfReader(files[0]);
		//pdf�ϲ�
	 	Rectangle rect=pdfReader.getPageSize(1);
		Document document = new Document(rect);
		PdfCopy copy=new PdfCopy(document,new FileOutputStream(res));
		document.open();
		for(int i=0;i<files.length;i++)
		{
			PdfReader  reader=new PdfReader(files[i]);
			int n=reader.getNumberOfPages();
			for(int j=1;j<=n;j++)
			{
				document.newPage();
				PdfImportedPage page=copy.getImportedPage(reader, j);
				copy.addPage(page);
			}
			reader.close();
		}
		document.close();
		pdfReader.close();
	}
	public static void addBackGround () throws Exception
	{
		//����
		String fromFile="d:/temp/merge_res.pdf";
		String bgFile="d:/temp/merge_res_bg.pdf";
		String markText="��������";
	 	
	 	String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		PdfReader  reader=new PdfReader(fromFile);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(bgFile));
		int n=reader.getNumberOfPages();
		Phrase phrase=new Phrase(markText,font);
		for(int i=1;i<=n;i++)
		{
			//PdfContentByte over=stamper.getOverContent(i);//ˮӡ���ı�֮��
			PdfContentByte over=stamper.getUnderContent(i); //ˮӡ���ı�֮��  
			over.saveState();
			PdfGState state=new PdfGState();
			state.setFillOpacity(0.2f);
			over.setGState(state);
			float beginPositionX=10,beginPositionY=70,distance=175;
			for(int i2=0;i2<4;i2++)
			{
				for(int j=0;j<4;j++)
				{
					ColumnText.showTextAligned(over, Element.ALIGN_LEFT,
								phrase, beginPositionX+distance*i2, beginPositionY+distance*j, 25);
				}
			}
			over.restoreState();
		}
		stamper.close();
		reader.close(); 
	}
	public static void genPdfBaseHtml () 
	{
		
	}
	public static void main(String[] args) throws Exception
	{
	
		
		//writePDF();//����OK
		//readPDF();//����OK����д
//		fillPdfTemplateVariable();//OK
		mergePdfFile () ;//OK
		//addBackGround() ;// �������� OK
		genPdfBaseHtml();
		
	}
}
