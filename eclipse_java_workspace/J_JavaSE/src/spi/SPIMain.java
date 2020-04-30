package spi;

import java.util.ServiceLoader;

public class SPIMain {

	public static void main(String[] arg) {
		//SPI的全名为Service Provider Interface
		//找所有classpath路径下 /META-INF/services/<接口全类名>   文件，内容为全类名的实现类
		ServiceLoader<Developer> serviceloader = ServiceLoader.load(Developer.class);
		//implements Iterable
		for (Developer dev : serviceloader) {
			System.out.println("find ." + dev.getPrograme());
		}
	}

}