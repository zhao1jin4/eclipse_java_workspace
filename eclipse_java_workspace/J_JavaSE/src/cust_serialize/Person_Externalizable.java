package cust_serialize;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;  

//ʵ��Externalizable�ӿڣ���ô���л���ϸ�ڽ��ɳ���Ա�����������л���Щ��Ա�������������л���˳��ͷ����л���˳��Ҫ����һ�¡�
public class Person_Externalizable implements Externalizable{  
      
    private String name;  
    //transient�ؼ��ֱ�ʾ�����л��ǣ�����������ü��룬���з����л���ʱ�򣬲��ܹ���ȡ�����ֵ  
    private  int age;  
      
    //ʵ��Externalizable���л��ӿڣ�����Ҫ�ṩ�޲ι��캯��  
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
     * ʵ�����л���ϸ�ڣ�������Щ�������л� 
     */  
    @Override  
    public void writeExternal(ObjectOutput out)   //��ObjectOutputStream
    			throws IOException {
        out.writeObject(name);  
        out.writeInt(age);  
    }  
      
    /** 
     * �����л��������л���Ա������˳��Ҫ�����л���˳��һֱ 
     */  
    @Override  
    public void readExternal(ObjectInput in)  //��ObjectInputStream   
    			throws IOException, ClassNotFoundException {
        name = (String)in.readObject();  
        age=in.readInt();  
    }  
}  