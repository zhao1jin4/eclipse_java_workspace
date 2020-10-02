package springdata_mongodb;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

//mongodb存的java.util.Date ,Timestamp可以存到mongodb中但取出有问题，要转
public class Date2TimestampConverter implements Converter<Date,Timestamp> 
{
	@Override
	public Timestamp convert(Date date) { 
		if(date==null)
			return null;
		else
			return new Timestamp(date.getTime());
	} 
}
