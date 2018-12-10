
/* 


//for JDK9
module J_JavaSE
 {
	 requires java.base;
	 requires java.desktop;
	 requires java.sql;
	 requires java.sql.rowset;
	 requires java.rmi;
	 requires java.instrument;
	 requires java.naming;
	 requires java.compiler;
	 requires java.xml;//在javax.xml.parsers.DocumentBuilderFactory;模块   org.w3c.dom
	 
//在JavaEE中的jar,还没有module  xx{ exports yy}
//   包javax.jws   javaWebSerice
	 
//	 包javax.annotation  在JavaEE中
	 requires java.xml.ws.annotation; //JDK 10 中  
	 
	 
//	 javaee 不兼容,除非注释 module的全部内容 
//		import javax.validation.Constraint;
	 
 }
  */

