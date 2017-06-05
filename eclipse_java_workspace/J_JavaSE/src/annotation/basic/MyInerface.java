package annotation.basic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Ĭ��annotation����Ϣ�Ǳ�����.class�ļ���,JVM����ȡ,ֻ����������ʹ��
@Retention(RetentionPolicy.RUNTIME)//Ĭ����CLASS(��class�ļ���),RUNTIME(��JVM �ɷ�������),SOURCE(������)
@Target({
	ElementType.METHOD,//��ʾ�����ڷ���ǰ
	ElementType.FIELD,//��ʾ����������ǰ
	ElementType.TYPE,//��ʾ��������/�ӿ�ǰ
	ElementType.CONSTRUCTOR,//��ʾ�����ڹ��췽��ǰ
	ElementType.PARAMETER////��ʾ�����ڷ���������
	})
@Documented  //�������ϵ���/����...,����javadocʱ,���Կ������ӵ�annotation������Ϣ
public @interface MyInerface //JVM �Զ�ʵ�� Annotation �ӿ�,������extends,implements�κ������� 
{
	public String[] value();//��һ����������value ������String,(���Ϳ���String ,����,Class,Annotation,Enum����Щ���͵�һά����)
	public String[] name() default "lisi";//������Ĭ��ֵ 
	Class type() default Object.class;
	Color color() default Color.red;
}
//enum Color { bule,yello,red};//��Ҫ�ⲿʹ��Ӧ���ڵ����ļ��ж���Ϊpublic enum
 