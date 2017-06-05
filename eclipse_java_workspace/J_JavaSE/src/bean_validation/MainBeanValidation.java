package bean_validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class MainBeanValidation {

	public static void main(String[] args) throws Exception {
		
		 
		 Order order = new Order(); 
		 order.setOrderId(""); 
		 order.setCustomer(""); 
		 order.setEmail(""); 
		 order.setStatus(""); 
		 order.setIsPay("X");
		 User user=new User();
		 user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-03"));
		 user.setPassword("abc123");
		 user.setRePassword("abc");
		 order.setUser(user);
		 
		 javax.el.ExpressionFactory dependsByValidation;
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
		 Validator validator = factory.getValidator(); 
		 Set<ConstraintViolation<Order>> violations = validator.validate(order); 
		 if(violations.size()> 0) 
		 {
			 StringBuffer buf = new StringBuffer(); 
			 ResourceBundle bundle = ResourceBundle.getBundle("bean_validation/message",Locale.CHINESE); 
			 for(ConstraintViolation<Order> violation: violations)
			 { 
				//bundle.getString(violation.getMessageTemplate() );//国际化的Key,或是自己的key
				buf.append( violation.getPropertyPath().toString()); 
				buf.append(violation.getMessage() ) .append("<BR>\n");
			 }
			 System.out.println(buf);
		 }
	}

}
