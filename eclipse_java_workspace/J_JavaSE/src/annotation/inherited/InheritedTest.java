package annotation.inherited;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited  //子类会继承父类的annotation,
@Retention(RetentionPolicy.RUNTIME)
public @interface InheritedTest 
{
	String value();
}
