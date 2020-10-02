package create.factory_method_clazz;
//工厂方法　　只一个抽像产品类(可多个实现),一个抽像工厂类和一个实现工厂类,抽像工厂类(一个方法)只能创建一种的抽像产品的实现
//在系统增加新的产品时只需要添加具体产品类和对应的具体工厂类，无须对原工厂进行任何修改，满足开闭原则；


//http://c.biancheng.net/view/1348.html  上工厂方法是 一个抽像产品类有多个实现，也要多个实现工厂类
//其缺点是：每增加一个产品就要增加一个具体产品类和一个对应的具体工厂类, 
//工厂方法模式只考虑生产同等级(同一个产品接口)的产品

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

