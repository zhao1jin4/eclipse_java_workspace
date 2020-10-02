package cust_serialize;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;  

//实现Externalizable接口，那么序列化的细节将由程序员处理，决定序列化哪些成员变量，而且序列化的顺序和反序列化的顺序要保持一致。
public class Person_Externalizable implements Externalizable{  
      
    private String name;  
    //transient关键字表示在序列化是，这个变量不用加入，所有反序列化的时候，不能够获取到这个值  
    private  int age;  
      
    //实现Externalizable序列化接口，必须要提供无参构造函数  
    public Person_Externalizable(){  
        System.out.println("no arg construction");  
    }  
    public Person_Externalizable(String name,int age) {  
        this.name=name;  
        this.age=age;  
    }  
    public String getName(){  
        return this.name;  
    }  
    public int getAge(){  
        return this.age;  
    }  
  
    @Override  
    public String toString() {  
        return name+" age is "+age+" country is ";  
    }  

      
    /** 
     * 实现序列化的细节，决定哪些对象序列化 
     */  
    @Override  
    public void writeExternal(ObjectOutput out)   //是ObjectOutputStream
    			throws IOException {
        out.writeObject(name);  
        out.writeInt(age);  
    }  
      
    /** 
     * 反序列化，反序列化成员变量的顺序要和序列化的顺序一直 
     */  
    @Override  
    public void readExternal(ObjectInput in)  //是ObjectInputStream   
    			throws IOException, ClassNotFoundException {
        name = (String)in.readObject();  
        age=in.readInt();  
    }  
}  