package create.prototype;

import java.beans.Beans;
import java.io.IOException;

 interface Prototype extends Cloneable {

    Object clone();

}

 

 class ConcretePrototype implements Prototype {

    public Object clone()
    {
        System.out.println("return new ConcretePrototype()");
        return new ConcretePrototype();
    }
}
 
public class Client {

       public static void main(String[] args) throws Exception
       {
    	   /*
    	     
    	     http://c.biancheng.net/view/1343.html
    	     	
   	     	原型模式通常适用于以下场景。
			    对象之间相同或相似，即只是个别的几个属性不同的时候。
			    对象的创建过程比较麻烦，但复制比较简单的时候。
			  
			  原型模式可扩展为带原型管理器
			*/
    	   	//实现Cloneable接口
    	   
    	   
              Prototype thePrototype = new ConcretePrototype();

              Prototype pt1 = (Prototype)thePrototype.clone();//prototype为clone
              DeepClone d=(DeepClone)  Beans.instantiate(null,"create.prototype.DeepClone");//实例化

       }

}