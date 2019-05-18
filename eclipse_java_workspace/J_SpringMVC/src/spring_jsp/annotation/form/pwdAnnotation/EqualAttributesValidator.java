package spring_jsp.annotation.form.pwdAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
public class EqualAttributesValidator implements ConstraintValidator<EqualAttributes, Object>
{
	private String firstAttribute;
	private String secondAttribute;
	@Override
	public void initialize(final EqualAttributes constraintAnnotation)
	{
		firstAttribute = constraintAnnotation.value()[0];
		secondAttribute = constraintAnnotation.value()[1]; 
	}
	@Override
	public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext)
	{
//		constraintContext.disableDefaultConstraintViolation();//禁用默认的message
		//使用新的提示
//		constraintContext.buildConstraintViolationWithTemplate("其中不能有空串").addConstraintViolation();
		
		if (object == null)
			return true;
		try
		{
			final Object first = PropertyUtils.getProperty(object, firstAttribute);
			final Object second = PropertyUtils.getProperty(object, secondAttribute);
			return new EqualsBuilder().append(first, second).isEquals();
		}
		catch (final Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
