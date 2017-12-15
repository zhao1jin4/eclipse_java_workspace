package spring_oxm.object;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

abstract class BaseOXM<T>
{
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private String fileName;
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	protected BaseOXM(String fileName)
	{
		this.fileName=fileName;
	}
	public void saveObject(T obj) throws IOException {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
			this.marshaller.marshal(obj, new StreamResult(os));
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	public T loadObject() throws IOException {
		FileInputStream is = null;
		try {
			is = new FileInputStream(fileName);
			return  (T)this.unmarshaller.unmarshal(new StreamSource(is));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
}