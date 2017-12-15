package org.zhaojin.memento;

public class TestMemento {
	//记念品,备忘录模式对叫快照 模式(snapshot),或Token模式
	//备忘Originator对象时如是对象类型要用clone来备份
	//自述历史模式(History-On-Self Pattern)是就是把发起人 和负责人 合并
	//徦如 协议 模式,将发起人做一个拷贝,检查如无效,抛异常,
	
	//session 对象保存的,浏览器只在cookie中保存JSESSIONID 是窄的,Server端保存很多数据 是宽的
	public static void main(String[] args) {
		
		
	}
	
	
	

	
	private void testOuter()
	{
	}
	class InnerClass
	{
		private void testInner()
		{
			testOuter();//可以调用外部类的private方法
		}
	}
	
}
