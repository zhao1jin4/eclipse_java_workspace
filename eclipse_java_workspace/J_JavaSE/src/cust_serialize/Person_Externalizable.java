package cust_serialize;
import java.io.Externalizable;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.ObjectInput;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutput;  
import java.io.ObjectOutputStream;  

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
    public static void main(String arg[]) throws IOException, ClassNotFoundException{  
        Person_Externalizable person=new Person_Externalizable("С��", 23);  
        System.out.println(person.toString());  
        FileOutputStream fileOutputStream=new FileOutputStream("c:/tmp/person2.out");  
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);  
        objectOutputStream.writeObject(person);  
        objectOutputStream.close();  
        FileInputStream fileInputStream=new FileInputStream("c:/tmp/person2.out");  
        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);  
        Person_Externalizable person_Externalizable=(Person_Externalizable)objectInputStream.readObject();  
        objectInputStream.close();  
        System.out.println(person_Externalizable);  
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