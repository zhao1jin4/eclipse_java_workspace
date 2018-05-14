
/*
  */

//for JDK9

module J_JavaSE
 {

     requires java.base;
     
//     包javax.jws 		          在 java.xml.ws 模块 下
	 requires java.xml.ws; 
	 
//	 包javax.annotation 		          在java.xml.ws.annotation 模块 下 JDK 10 中 
	 requires java.xml.ws.annotation; //JDK 10 中 
	 
	 
	 
	 requires java.desktop;
	 requires java.sql;
	 requires java.sql.rowset;
	 requires java.rmi;
	 requires java.instrument;
	 requires  java.naming;
	 requires java.compiler;
	 requires  java.xml;//在javax.xml.parsers.DocumentBuilderFactory;模块 
	 
//	 javaee 不兼容,除非注释 module的全部内容 
//		import javax.validation.Constraint;
	 
 }


