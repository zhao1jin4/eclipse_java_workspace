package cust_serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyCustSerialize {

    public static void main(String arg[]) throws IOException, ClassNotFoundException{  
        Person_Externalizable person=new Person_Externalizable("ะกอ๕", 23);  
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
      

}
