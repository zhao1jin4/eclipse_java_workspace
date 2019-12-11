package yaml_snake;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

public class SnakeYamlMain {
	//���� yaml-1.2 ��2009���ƶ���  
	//SnakeYaml ֻ֧�� yaml-1.1 , SpringBootʹ�����
	/* 
  <dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>1.25</version>
</dependency>
	 */
	//yaml�ļ�  :��һ���пո�
	public static void main(String[] args) {
		try {
			readYaml() ;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void readYaml( ) throws Exception {
		Yaml yaml = new Yaml();
		URL url = SnakeYamlMain.class.getResource("test.yaml");
		if (url != null) {
			// Ҳ���Խ�ֵת��ΪMap
			Map map = (Map) yaml.load(new FileInputStream(url.getFile()));
			System.out.println(map);
			
			Map map1 =   yaml.loadAs(new FileInputStream(url.getFile()),Map.class);
			System.out.println(map);
			printMap(map1,0);
		}
	}
	public static void writeYaml( ) throws Exception {
		Yaml yaml = new Yaml();
		
	}
	
	 private static void printMap(Map map, int level){
	        Set set = map.keySet();
	        for(Object key: set){

	            Object value = map.get(key);

	            for(int i=0; i<level; i++){
	                System.out.print("    ");
	            }

	            if(value instanceof Map)
	            {
	                System.out.println(key+":");
	                printMap((Map)value, level+1);//Ƕ��
	            }else if(value instanceof List)
	            {

	                System.out.println(key+":");
	                for(Object obj: (List)value)
	                {
	                    for(int i=0; i<level; i++)
	                    {
	                        System.out.print("    ");
	                    }
	                   if(obj instanceof Map)
	                   {
	                	   System.out.print(" - ");
	                	   printMap((Map)obj, 0);//Ƕ�� ,�����Ӽ������ܾͲ�����(level 0)????
	                   }
	                   else
	                	   System.out.println(" - "+obj.toString());
	                }
	            }else {

	                System.out.println(key + ": " + value);
	            }
	        }
	    }
}
