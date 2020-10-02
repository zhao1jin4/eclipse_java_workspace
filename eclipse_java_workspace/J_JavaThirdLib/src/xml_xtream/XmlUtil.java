package xml_xtream;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {

	public static String objToXml(Object srcObj) {

		String xml = null;
		XStream xStream = new XStream(new DomDriver());
		xStream.autodetectAnnotations(true);
		xml = xStream.toXML(srcObj);
		return xml;
	}
 
	public static <T>  T xmlToObj(String xml,Class<T> clazz) {
		if(xml == null){
			return null;
		}
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(clazz); 
		Object obj = xStream.fromXML(xml);
		return (T)obj;
	}
}
