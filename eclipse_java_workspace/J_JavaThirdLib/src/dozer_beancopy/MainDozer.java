package dozer_beancopy;

import java.util.Date;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

public class MainDozer {
	public static void main(String[] args) {
		//复制Bean属性
		
		SourceClassName sourceObject = new SourceClassName();
		sourceObject.setName("Dozer");
		sourceObject.setBirthday(new Date());
		sourceObject.setPair(new NestPair("one","two"));
		
		BeanMappingBuilder builder=new BeanMappingBuilder() {
			@Override
			protected void configure() {
				//从哪个类，复制为什么类时，排除哪个字段,如JPA lazy字段
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
