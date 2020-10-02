package jpa_school;

import java.util.Objects;

import javax.persistence.AttributeConverter;

//Database Y/N -> Java Boolean
public class YNConverter implements AttributeConverter<Boolean,String>{ 
	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		if(Objects.nonNull(attribute) && attribute)
			return "Y";
		else
			return "N";
	} 
	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		if("Y".equalsIgnoreCase(dbData))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	} 
}
