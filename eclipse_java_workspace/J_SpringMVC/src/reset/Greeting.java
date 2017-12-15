package reset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "greeting")
public class Greeting {

    private final long id;
    private final String content;
    public Greeting() //µ±ÊÇ  XmlRootElement
    {
    	id=0;
    	content="";
    }
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
    //@XmlElement
    public long getId() {
        return id;
    }
    //@XmlElement
    public String getContent() {
        return content;
    }
}