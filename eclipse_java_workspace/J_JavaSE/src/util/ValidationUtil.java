package util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
 

public class ValidationUtil {
	
	public static <T> String  validate(T ojb )
	{ 
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
			 }
			 System.out.println(buf);
			 return buf.toString();
		 }
		 return null;
	}

}
