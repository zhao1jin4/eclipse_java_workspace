package instrument;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;


public class Premain 
{ 
//	在 main 函数运行之前执行
//	先找 	 public static void premain(String agentArgs, Instrumentation inst); 
//	如无再找 public static void premain(String agentArgs); 
	
    public static void premain(String agentArgs, Instrumentation inst)   { 
    	System.out.println("enter premain"); 
//        inst.addTransformer(new MyClassFileTransformer()); 
        //-------------------方式2
    	byte[] bytes = MyClassFileTransformer.getBytesFromFile(MyClassFileTransformer.classNumberReturns2);
    	ClassDefinition def = new ClassDefinition(TransClass.class,bytes); 
        try {
			inst.redefineClasses(new ClassDefinition[] { def });
		} catch (ClassNotFoundException | UnmodifiableClassException e) {
			e.printStackTrace();
		} 
        System.out.println("success"); 
        
    } 
 }