package annotation.basic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//默认annotation是信息是保存在.class文件中,JVM不读取,只被其它工具使用
@Retention(RetentionPolicy.RUNTIME)//默认是CLASS(放class文件中),RUNTIME(放JVM 可反射来读),SOURCE(不编译)
@Target({
	ElementType.METHOD,//表示可用在方法前
	ElementType.FIELD,//表示可用在属性前
	ElementType.TYPE,//表示可用在类/接口前
	ElementType.CONSTRUCTOR,//表示可用在构造方法前
	ElementType.PARAMETER////表示可用在方法参数中
	})
@Documented  //对做用上的类/方法...,生成javadoc时,可以看到符加的annotation声明信息
public @interface MyInerface //JVM 自动实现 Annotation 接口,不能在extends,implements任何其它的 
{
	public String[] value();//定一个变量名是value 类型是String,(类型可是String ,基本,Class,Annotation,Enum及这些类型的一维数组)
	public String[] name() default "lisi";//可以有默认值 
	Class type() default Object.class;
	Color color() default Color.red;
}
//enum Color { bule,yello,red};//如要外部使用应该在单独文件中定义为public enum
 