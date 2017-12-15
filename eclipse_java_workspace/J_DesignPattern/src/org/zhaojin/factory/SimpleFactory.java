package org.zhaojin.factory;
/**
 * 简单工厂  又叫  静态工厂方法 ,是工厂方法的特殊实现
 * @author zhaojin
 *
 */
public class SimpleFactory 
{
	public static void main (String[] args) throws Exception
	{
		FruitSimple fruit=FruitGardener.factory("apple");
		fruit.grow();
	}

} 

 class FruitGardener  // 简单工厂和工厂方法不一样的是没有抽像工厂类
{
    public static FruitSimple factory(String which) throws    Exception
    {
        if (which.equalsIgnoreCase("apple"))
        {
            return new Apple();
        }
        else if (which.equalsIgnoreCase("grape"))
        {
            return new Grape();
        }
        else
        {
         	throw new  Exception("Bad fruit request");
        }
    }
}
 interface FruitSimple
 {
     void grow();

     void harvest();

     void plant();
 }
 class Grape implements FruitSimple
 {
     public void grow()
     {
     	System.out.println("Grape is growing...");
     }

     public void harvest()
     {
     	System.out.println("Grape has been harvested.");
     }

     public void plant()
     {
         System.out.println("Grape has been planted.");
     }

     public boolean getSeedless()
     {
         return seedless;
     }

     public void setSeedless(boolean seedless)
     {
         this.seedless = seedless;
     }

     private boolean seedless;
 }
 class Apple implements FruitSimple
 {

     public void grow()
     {
     	System.out.println("Apple is growing...");
     }

     public void harvest()
     {
     	System.out.println("Apple has been harvested.");
     }

     public void plant()
     {
         System.out.println("Apple has been planted.");
     }

     public int getTreeAge(){ return treeAge; }

     public void setTreeAge(int treeAge){ this.treeAge = treeAge; }

     private int treeAge;
 }

 
 
 
 
 