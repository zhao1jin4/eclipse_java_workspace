package apache_xalan;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class TestXalan {

	public static void main(String[] args) {
		TransformerFactory   tFactory=TransformerFactory.newInstance();
		System.getProperty("javax.xml.transform.TransformerFactory");//org.apache.xalan.processor.TransformerFactoryImpl
		//xx.jar/META-INF/services/javax.xml.transform.TransformerFactory文件
		//java org.apache.xalan.xslt.Process -IN student.xml -XSL student.xsl -OUT student.html
		try {
			Transformer   transformer=tFactory.newTransformer();//new StreamSource("")可传XSL文件
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

}
