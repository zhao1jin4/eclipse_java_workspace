package zxing_qr_code;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class GenerateQR {

	public static void main(String[] args) {
		try {
			
			 final int BLACK = 0xFF000000;
			 final int WHITE = 0xFFFFFFFF;
		     String content = "http://www.baidu.com";
		     String fileName = "C:/test_QR.jpg";
		     
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     Map<EncodeHintType,String> hints = new HashMap<EncodeHintType,String>();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
		     File file = new File(fileName);
		     
		     int width = matrix.getWidth();
		     int height = matrix.getHeight();
		     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		     for (int x = 0; x < width; x++) 
		     {
		       for (int y = 0; y < height; y++) 
		       {
		         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
		       }
		     }
		     ImageIO.write(image, "jpg", file) ;
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }

	}

}
