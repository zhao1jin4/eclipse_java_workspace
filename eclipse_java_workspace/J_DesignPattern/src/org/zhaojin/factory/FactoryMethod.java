package org.zhaojin.factory;
//工厂方法　　只一个抽像产品类(可多个实现),一个抽像工厂类和一个实现工厂类,抽像工厂类(一个方法)只能创建一种的抽像产品的实现
public class FactoryMethod
{
	public static void main(String[] args) {

		Factory mFactory=new ConcreteFactory();//实例化具体工厂
		Product mProductA=mFactory.createProduct(ConcreteProductA.class);//根据类名初始化具体产品
		mProductA.method();
		Product mProductB=mFactory.createProduct(ConcreteProductB.class);
		mProductB.method();
		
	}
}
abstract class Product { //产品类的抽象方法，有具体的产品类去实现 
	public abstract void method(); 
	 
}

 class ConcreteProductA extends Product {

	public void method() {
		System.out.println("我是具体产品类A");
	}

}

 class ConcreteProductB extends Product {
		public void method() {
			System.out.println("我是具体产品类B");
		}
}
 
abstract class Factory  {
		//抽象的工厂方法，具体生产什么有子类去实现
      public abstract  Product createProduct(Class  clz);
}


class ConcreteFactory  extends Factory  {
	/* 
	 * 需要哪一个类就传入哪一个类的类型
	 */
	public  Product createProduct(Class clz) {//传入一个class类要决定生产哪一个产品
		  Product p=null;
		  try {
			p=(Product) Class.forName(clz.getName()).newInstance();//根据类加载器实例化对象
		}  catch (Exception e) {			
			e.printStackTrace();
		}
		return   p;
	}
}

