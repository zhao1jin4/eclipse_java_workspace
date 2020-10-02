package dozer_beancopy;

import java.util.Date;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

public class MainDozer {
	public static void main(String[] args) {
		//����Bean����
		
		SourceClassName sourceObject = new SourceClassName();
		sourceObject.setName("Dozer");
		sourceObject.setBirthday(new Date());
		sourceObject.setPair(new NestPair("one","two"));
		
		BeanMappingBuilder builder=new BeanMappingBuilder() {
			@Override
			protected void configure() {
				//���ĸ��࣬����Ϊʲô��ʱ���ų��ĸ��ֶ�,��JPA lazy�ֶ�
				mapping(NestPair.class, NestPair.class, TypeMappingOptions.mapNull(false)).exclude("second");
			}
		};
		Mapper mapper = DozerBeanMapperBuilder.create().withMappingBuilder(builder).build();
		//Mapper mapper = DozerBeanMapperBuilder.buildDefault(); 
		
		DestinationClassName destObject = mapper.map(sourceObject, DestinationClassName.class);
		
		System.out.println(destObject.getBirthday().equals(sourceObject.getBirthday()));
		System.out.println(destObject.getPair() );
	}
}
