package fastDFS;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.csource.common.DFSException;
import org.csource.common.NameValuePair;

public class MainFastDFS 
{
	public   static void main(String... args) throws Exception
	{
		Class clazzClientGlobal=Class.forName("org.csource.fastdfs.ClientGlobal");
		Constructor construct=clazzClientGlobal.getDeclaredConstructors()[0];
		construct.setAccessible(true);
		 Object obj=construct.newInstance(null);
		 ClientGlobal global=(ClientGlobal)obj ;// Spring注入反射实例化
		
		 global.setP_g_connect_timeout(2000);
		 global.setP_g_connect_timeout(2000);
		 global.setP_g_charset("UTF-8");
		 global.setP_g_tracker_http_port(8080);
		 global.setP_g_anti_steal_token(false); 
		 global.setP_g_secret_key("");
		 //global.setP_tracker_servers("172.16.35.35:22122");//多个以,分隔
		 global.setP_tracker_servers("172.16.37.41:22122,172.16.37.40:22122");//测试OK
		 global.init1();
		StorageClient1 stclient=new StorageClient1(global);//Spring注入

		byte[] byteArray =getExcelArray();
		Date now=Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		NameValuePair[] meta_list = new NameValuePair[]{
				  new NameValuePair("fileName", "excel数据.xls"),
				  new NameValuePair("extName", "exls"),
				  new NameValuePair("size",  byteArray.length+""),
//				  new NameValuePair("md5", ""), 
//				  new NameValuePair("contentType", ""),
				  new NameValuePair("uploadDate", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date())), 
				  new NameValuePair("creator", "lisi")
		};
         
		Lock lock=new ReentrantLock();
		try{
			lock.tryLock(30, TimeUnit.SECONDS);
			//不能两个文件同时上传，如jquery , fileupload插件，当<input type="file" multiple >多选时就会两个同时上传报错
	       String filePath= stclient.upload_file1(byteArray, "xls", meta_list);
	       System.out.println(filePath);
		}finally {
			lock.unlock();
		}
     
       
	}
	public static byte[] getExcelArray() throws Exception  
	{
		 int rows=0;
		 int columns=0;
		 XSSFWorkbook	webbook=new XSSFWorkbook();
		Sheet sheet = webbook.createSheet();
		webbook.setSheetName(0, "第一页");
		int default_width=sheet.getColumnWidth(columns);
		sheet.setColumnWidth(columns, default_width*2);
		
		for(int i=0;i<100;i++)
		{
			
			Row row = null;
			if(columns==0)
				row = sheet.createRow(rows);
			else
				row = sheet.getRow(rows);
			Cell cell = row.createCell(columns);
			cell.setCellValue("数值"+i);
			if(rows<50-1) //excel 每列50行
			{
				rows++;
			}else
			{
			 	rows=0;
				columns++;
				sheet.setColumnWidth(columns, default_width*2);
			}
		}
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		webbook.write(outPut);
		byte[] byteArray = outPut.toByteArray();
		return byteArray;
			
	}
}
