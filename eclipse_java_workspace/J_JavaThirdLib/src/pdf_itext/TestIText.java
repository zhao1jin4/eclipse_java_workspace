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
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		Paragraph p1=new Paragraph("_Hello 你好_",font);
		p1.setIndentationLeft(10f);
		p1.setIndentationRight(10f);
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
		String strImg=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
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
		String pdfFile = "D:\\book\\云\\《疯狂Spring Cloud》电子书（一）.pdf";
	
		PdfReader reader = new PdfReader(pdfFile);//读已经存在PDF
		System.out.println(reader.getPdfVersion());
		
		//---读写
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("d:/temp/itext_out.pdf"));
		//建立pdf后,ttf文件不存在也可以(可能是系统中有)
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
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
			  String strImg=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/google.png";
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
//		world 创建模板文件 另存为(libreOffice导出) pdf  -> Adobe Acrobat Reader Proc DC  再编辑 PDF 
//		->准备表单->工具栏上 添加"文本"域 ,拖出一个区域, 设置变量,itext程序就可以赋值 
		
		Map<String,String> param =new HashMap<>();
		param.put("my_username", "张三");
		param.put("my_age", "20");
		
		String pdfFile =dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/template_user.pdf";
		PdfReader reader = new PdfReader(pdfFile);//读已经存在PDF
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:/temp/template_user_res.pdf"));
		stamper.getUnderContent(1);
		 
		String ttf=dirPrefix+"/eclipse_java_workspace/J_JavaThirdLib/src/pdf_itext/simhei.ttf";
		//String ttf="c:\\WINDOWS\\Fonts\\SIMHEI.TTF";
		BaseFont chineseFont = BaseFont.createFont(ttf ,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		Font font = new Font(chineseFont, 12, Font.NORMAL);
		
		
		//设置模板变量
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
		//pdf合并
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
		//背景
		String fromFile="d:/temp/merge_res.pdf";
		String bgFile="d:/temp/merge_res_bg.pdf";
		String markText="背景文字";
	 	
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
			//PdfContentByte over=stamper.getOverContent(i);//水印在文本之上
			PdfContentByte over=stamper.getUnderContent(i); //水印在文本之下  
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
	
		
		//writePDF();//中文OK
		//readPDF();//中文OK，读写
//		fillPdfTemplateVariable();//OK
		mergePdfFile () ;//OK
		//addBackGround() ;// 背景文字 OK
		genPdfBaseHtml();
		
	}
}
