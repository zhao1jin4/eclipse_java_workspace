package springdata_mongodb;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

//mongodb���java.util.Date ,Timestamp���Դ浽mongodb�е�ȡ�������⣬Ҫת
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
