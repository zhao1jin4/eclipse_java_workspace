package spring_jsp.annotation.form.pwdAnnotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;


@Retention(RUNTIME)
@Constraint(validatedBy = EqualAttributesValidator.class)//自这定义的类
@Documented
public @interface EqualAttributes
{
	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] value();
}
