package instrument;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;


public class Premain 
{ 
    public static void premain(String agentArgs, Instrumentation inst)   { 
    	System.out.println("enter premain"); 
//        inst.addTransformer(new MyClassFileTransformer()); 
        //-------------------·½Ê½2
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