package zxing_line_code;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ParserLine {

	public static void main(String[] args) throws Exception {
		 String imgPath = "c:/line.png";  
		
		 BufferedImage  image = ImageIO.read(new File(imgPath));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Result result = new MultiFormatReader().decode(bitmap, null);
		System.out.println(result.getText());  
	}

}
