package spi;

import java.util.ServiceLoader;

public class SPIMain {

	public static void main(String[] arg) {
		//SPI��ȫ��ΪService Provider Interface
		//������classpath·���� /META-INF/services/<�ӿ�ȫ����>   �ļ�������Ϊȫ������ʵ����
		ServiceLoader<Developer> serviceloader = ServiceLoader.load(Developer.class);
		//implements Iterable
		for (Developer dev : serviceloader) {
			System.out.println("find ." + dev.getPrograme());
		}
	}

}