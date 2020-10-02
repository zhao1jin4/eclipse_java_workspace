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
protoc.exe -I=proto������Ŀ¼ --java_out=java�����Ŀ¼ proto������Ŀ¼����proto�ļ�


*/
	public static void main(String[] args) throws Exception {
		//ģ�⽫����ת��byte[]�����㴫��
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

        //ģ�����Byte[]�������л���Person��
        byte[] byteArray =person.toByteArray();
        PersonEntity.Person p2 = PersonEntity.Person.parseFrom(byteArray);
        System.out.println("after :" +p2.toString());

	}

}
