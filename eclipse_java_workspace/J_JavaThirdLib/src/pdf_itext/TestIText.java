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
		
	   
		//--fail 支持中文,要加对应版本 iTextAsian.jar,要下载安装包才行的,并没有真正的嵌入到pdf文件??????
//		BaseFont chineseFont = BaseFont.createFont("STSong-Light,Bold", "UniGB-UCS2-H", BaseFont.EMBEDDED);
//		Font font =  new Font(chineseFont);
		
		//--fail
//		FontSelector selector = new FontSelector();
//		//FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new BaseColor(255, 150, 200));
//		selector.addFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
//        selector.addFont(FontFactory.getFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
//        document.add(selector.process("hello 你妹啊"));
		//---OK
		//建立pdf后,ttf文件不存在也可以(可能是系统中有)
		String ttf="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		Paragraph p1=new Paragraph("_Hello 你好_",font);
		document.add(p1);
		document.newPage();   //换页 
		
		Paragraph p2=new Paragraph(10,"_表格内容_",font);
		PdfPCell title = new PdfPCell(p2);
		title.setColspan(2);
		title.setBackgroundColor(BaseColor.RED);
		int numColumns = 2;
		PdfPTable table = new PdfPTable(numColumns);
		table.addCell(title);
		document.add(table);
		
		
		document.newPage();   //换页   
		 //可以是绝对路径，也可以是URL
		String strImg="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
		 Image img = Image.getInstance(strImg);   
		 // Image image = Image.getInstance(new URL(http://xxx.com/logo.jpg));   
		 img.setAbsolutePosition(0, 0);   
		 document.add(img);
		 
		 
		document.close();
		
		/* 
		//如果是Web
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
		PdfReader reader = new PdfReader("D:/my/Spring_源码分析.pdf");//读已经存在PDF
		System.out.println(reader.getPdfVersion());
		
		//---读写
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("d:/temp/itext_out.pdf"));
		//建立pdf后,ttf文件不存在也可以(可能是系统中有)
		String ttf="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		for (int i=1; i<=reader.getNumberOfPages(); i++)
		{
			  //获得pdfstamper在当前页的上层打印内容。也就是说 这些内容会覆盖在原先的pdf内容之上。
			  PdfContentByte over = stamper.getOverContent(i);
			  //用pdfreader获得当前页字典对象。包含了该页的一些数据。比如该页的坐标轴信息。
			  PdfDictionary p = reader.getPageN(i);
			  //拿到mediaBox 里面放着该页pdf的大小信息。
			  PdfObject po =  p.get(new PdfName("MediaBox"));
			  System.out.println(po.isArray());
			  //po是一个数组对象。里面包含了该页pdf的坐标轴范围。
			  PdfArray pa = (PdfArray) po;
			  System.out.println(pa.size());
			  //看看y轴的最大值。
			  System.out.println(pa.getAsNumber(pa.size()-1));
			  //开始写入文本
			  over.beginText();
			  //设置字体和大小
			  over.setFontAndSize(font.getBaseFont(),10);
			  //设置字体的输出位置
			  over.setTextMatrix(107, 540);
			  //要输出的text
			  over.showText("我要加[终稿]字样 " + i);
			  over.endText();
			  //创建一个image对象。
			  String strImg="D:/Program/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
			  Image image = Image.getInstance(strImg);
			  //设置image对象的输出位置pa.getAsNumber(pa.size()-1)。floatValue() 是该页pdf坐标轴的y轴的最大值
			  image.setAbsolutePosition(0,pa.getAsNumber(pa.size()-1).floatValue()-100);//0, 0, 841.92, 595.32
			  over.addImage(image);
			  //画一个圈。
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
