package bean_validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualAttributesValidator.class)//自这定义的类
@Documented
public @interface EqualAttributes
{
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String[] value();
}
