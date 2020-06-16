package behavior.memento;

public class TestMemento {
	//备忘录模式能记录一个对象的内部状态，当用户后悔时能撤销当前操作，使数据恢复到它原先的状态。
	//除了创建它的发起人之外，其他对象都不能够访问这些状态信息。
	//发起人 不需要管理和保存其内部状态的各个备份，所有状态信息都保存在备忘录中，并由管理者进行管理，这符合单一职责原则。
	
	//memento 记念品,备忘录模式 又叫 快照模式(snapshot)
	//originator发起人 
	//备忘录模式如何同原型模式混合使用 ,clone来备份
	
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
