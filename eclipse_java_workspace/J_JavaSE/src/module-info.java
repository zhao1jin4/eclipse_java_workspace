
/*
  */

//for JDK9

module J_JavaSE
 {

     requires java.base;
     
//     ��javax.jws 		          �� java.xml.ws ģ�� ��
	 requires java.xml.ws; 
	 
//	 ��javax.annotation 		          ��java.xml.ws.annotation ģ�� �� JDK 10 �� 
	 requires java.xml.ws.annotation; //JDK 10 �� 
	 
	 
	 
	 requires java.desktop;
	 requires java.sql;
	 requires java.sql.rowset;
	 requires java.rmi;
	 requires java.instrument;
	 requires  java.naming;
	 requires java.compiler;
	 requires  java.xml;//��javax.xml.parsers.DocumentBuilderFactory;ģ�� 
	 
//	 javaee ������,����ע�� module��ȫ������ 
//		import javax.validation.Constraint;
	 
 }


