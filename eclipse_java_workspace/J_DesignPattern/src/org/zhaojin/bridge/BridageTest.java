package org.zhaojin.bridge;

public class BridageTest 
{

	
	
	public static void main(String[] args)
	{
		//JDBC Driver 的应用 
		java.awt.peer.ScrollbarPeer bar=null;
		//peer包在JDK API中是没有的
	}

}
/*
 abstract class Coffee
{
CoffeeImp coffeeImp;
public void setCoffeeImp() {
this.coffeeImp = CoffeeImpSingleton.getTheCoffeImp();
}
public CoffeeImp getCoffeeImp() {return this.CoffeeImp;}
public abstract void pourCoffee();
} 
// 其中CoffeeImp 是加不加奶的行为接口,看其代码如下: 

 abstract class CoffeeImp
{
public abstract void pourCoffeeImp();
} 
  //  现在我们有了两个抽象类,下面我们分别对其进行继承,实现concrete class: 

//中杯 
 MediumCoffee extends Coffee
{
public MediumCoffee() {setCoffeeImp();}
public void pourCoffee()
{
CoffeeImp coffeeImp = this.getCoffeeImp();
//我们以重复次数来说明是冲中杯还是大杯 ,重复2次是中杯 
for (int i = 0; i < 2; i++)
{
coffeeImp.pourCoffeeImp();
}

}
}
//大杯 
 SuperSizeCoffee extends Coffee
{
public SuperSizeCoffee() {setCoffeeImp();}
public void pourCoffee()
{
CoffeeImp coffeeImp = this.getCoffeeImp();
//我们以重复次数来说明是冲中杯还是大杯 ,重复5次是大杯 
for (int i = 0; i < 5; i++)
{
coffeeImp.pourCoffeeImp();
}

}
} 
    //上面分别是中杯和大杯的具体实现.下面再对行为CoffeeImp进行继承: 

//加奶 
 class MilkCoffeeImp extends CoffeeImp
{
MilkCoffeeImp() {}
public void pourCoffeeImp()
{
System.out.println("加了美味的牛奶");
}
}
//不加奶 
class FragrantCoffeeImp extends CoffeeImp
{
	FragrantCoffeeImp() {}
	public void pourCoffeeImp()
	{
	System.out.println("什么也没加,清香");
	}
}


class CoffeeImpSingleton
{
	private static CoffeeImp coffeeImp;
	public CoffeeImpSingleton(CoffeeImp coffeeImpIn)
	{
		this.coffeeImp = coffeeImpIn;
	}
　　public static CoffeeImp getTheCoffeeImp()
　　{
　　　　return coffeeImp;
　　}
} 
 */

