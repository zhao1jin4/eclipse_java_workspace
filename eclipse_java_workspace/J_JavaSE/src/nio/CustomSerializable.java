package nio;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

public class CustomSerializable {

	public static void main(String[] args) throws Exception {
		
		
		 Person alice = new Person("Alice", 10);
         ObjectOutputStream out = new ObjectOutputStream(System.out);
         out.writeObject(alice);

         ObjectInputStream in = new ObjectInputStream(System.in);
         Person person = (Person) in.readObject();
         System.out.println(person);

         
	}

}
class Person implements Externalizable {

    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("My name is %s, and I'm %d years old.",
                name, age);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        int strLen = (name == null) ? -1 : name.length();
        out.writeInt(strLen);

        if (strLen > 0) {
            out.write(name.getBytes(Charset.forName("UTF-8")));
        }

        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int strLen = in.readInt();
        if (strLen <= -1) {
            name = null;
        } else if (strLen == 0) {
            name = "";
        } else {
            byte[] strBytes = new byte[strLen];
            in.readFully(strBytes);
            name = new String(strBytes, Charset.forName("UTF-8"));
        }

        age = in.readInt();
    }
}
