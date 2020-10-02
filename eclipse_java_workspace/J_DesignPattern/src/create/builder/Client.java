package create.builder;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class Client
{
    public static void main(String[] args)
    {
    	//创建一个复杂的对象，这个复杂对象通常由多个子部件按一定的步骤组合而成
    	//建造者模式注重零部件的组装过程，而工厂方法模式更注重零部件的创建过程，但两者可以结合使用。
    	//可以省略掉抽象建造者，甚至可以省略掉指挥者角色。
        Builder builder=new ConcreteBuilder();
        Director director=new Director(builder);
        Product product=director.construct();
        product.show();
        
        		
    }
    public static void jdk( ) throws Exception
    {

        DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=dFactory.newDocumentBuilder();
        Document doc;                           
        doc=builder.parse(new File("src/Builder/config.xml"));
    }
}