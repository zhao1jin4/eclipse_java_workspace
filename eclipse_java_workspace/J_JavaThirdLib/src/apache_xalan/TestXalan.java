package apache_xalan;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class TestXalan {

	public static void main(String[] args) {
		TransformerFactory   tFactory=TransformerFactory.newInstance();
		System.getProperty("javax.xml.transform.TransformerFactory");//org.apache.xalan.processor.TransformerFactoryImpl
		//xx.jar/META-INF/services/javax.xml.transform.TransformerFactory�ļ�
		//java org.apache.xalan.xslt.Process -IN student.xml -XSL student.xsl -OUT student.html
		try {
			Transformer   transformer=tFactory.newTransformer();//new StreamSource("")�ɴ�XSL�ļ�
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

}
