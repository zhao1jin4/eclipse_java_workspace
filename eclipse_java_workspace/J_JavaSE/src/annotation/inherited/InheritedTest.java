package annotation.inherited;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited  //�����̳и����annotation,
@Retention(RetentionPolicy.RUNTIME)
public @interface InheritedTest 
{
	String value();
}
