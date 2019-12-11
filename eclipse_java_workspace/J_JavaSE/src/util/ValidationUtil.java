package util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
 

public class ValidationUtil {
	
	public static <T> Map<String,String>  validate(T ojb )
	{ 
		Map<String,String>  map=new HashMap<>();
		 javax.el.ExpressionFactory dependsByValidation;
		 
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
		 Validator validator = factory.getValidator(); 
		 Set<ConstraintViolation<T>> violations = validator.validate(ojb); 
		 if(violations.size()> 0) 
		 {
			 StringBuffer buf = new StringBuffer(); 
			 //ResourceBundle bundle = ResourceBundle.getBundle("bean_validation/message",Locale.CHINESE); 
			 for(ConstraintViolation<T> violation: violations)
			 { 
				//bundle.getString(violation.getMessageTemplate() );//国际化的Key,或是自己的key
				buf.append(violation.getPropertyPath().toString()); 
				buf.append(":");
				buf.append(violation.getMessage() ) .append("<BR>\n");
				String key=violation.getPropertyPath().toString();
				
				if(map.get(key)!=null)
					map.put(violation.getPropertyPath().toString(),map.get(key)+ "<BR>\n"+ violation.getMessage()+ "<BR>\n");
				else
					map.put(violation.getPropertyPath().toString(), violation.getMessage()+ "<BR>\n");
			 }
			 System.out.println(buf);
		 }
		 return map;
	}

}
