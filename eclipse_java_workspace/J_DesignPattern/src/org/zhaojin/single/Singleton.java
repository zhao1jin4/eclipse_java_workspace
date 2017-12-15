package org.zhaojin.single;
 public class Singleton {  //静态内部类
     private static class SingletonHolder {  
    	 private static final Singleton INSTANCE = new Singleton();  
     }  
     private Singleton (){}
     public static final Singleton getInstance() {  
         return SingletonHolder.INSTANCE;  
     }  
 }  
