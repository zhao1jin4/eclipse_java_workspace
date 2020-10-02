package serial_protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class MainProtoBuf {
	/*
	<dependency>
    <groupId>com.google.protobuf</groupId>
    <artifactId>protobuf-java</artifactId>
    <version>3.5.1</version>
</dependency>
protoc -I=. --java_out=. persion.proto   
protoc.exe -I=proto的输入目录 --java_out=java类输出目录 proto的输入目录包含proto文件


*/
	public static void main(String[] args) throws Exception {
		//模拟将对象转成byte[]，方便传输
        PersonEntity.Person.Builder builder = PersonEntity.Person.newBuilder();
        builder.setId(1);
        builder.setName("ant");
        builder.setEmail("ghb@soecode.com");
        PersonEntity.Person person = builder.build();
        System.out.println("before :"+ person.toString());

        System.out.println("===========Person Byte==========");
        for(byte b : person.toByteArray()){
            System.out.print(b);
        }
        System.out.println();
        System.out.println(person.toByteString());
        System.out.println("================================");

        //模拟接收Byte[]，反序列化成Person类
        byte[] byteArray =person.toByteArray();
        PersonEntity.Person p2 = PersonEntity.Person.parseFrom(byteArray);
        System.out.println("after :" +p2.toString());

	}

}
