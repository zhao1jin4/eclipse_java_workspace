package zxing_line_code;
import java.net.URI;
import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class GenerateLine
{  
  
    public static void main(String[] args)  throws Exception{  
    	 String imgPath = "file:///c:/line.png";  
         String contents = "6923450657713";  // 益达无糖口香糖的条形码  

         int width = 105, height = 50;  
         int codeWidth = 3 + // start guard  
                 (7 * 6) + // left bars  
                 5 + // middle guard  
                 (7 * 6) + // right bars  
                 3; // end guard  
         codeWidth = Math.max(codeWidth, width);  
         BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.EAN_13, codeWidth, height, null);
         MatrixToImageWriter.writeToPath(bitMatrix, "png",Paths.get(new URI(imgPath)));
    } 
}