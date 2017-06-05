package org.zhaojin.prototype;

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
    	   	//实现Cloneable接口
    	   
    	   
              Prototype thePrototype = new ConcretePrototype();

              Prototype pt1 = (Prototype)thePrototype.clone();//prototype为clone
              DeepClone d=(DeepClone)  Beans.instantiate(null,"org.zhaojin.prototype.DeepClone");//实例化

       }

}