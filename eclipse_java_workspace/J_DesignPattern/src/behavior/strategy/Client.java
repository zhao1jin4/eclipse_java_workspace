package behavior.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//环境类 ,持有一个策略类的引用，最终给客户端调用
class Context {

	private Strategy stg;

	public Context(Strategy theStg) {
		this.stg = theStg;
	}

	public void DoAction() { //可在client中得到策略类的引用再调用 
		this.stg.AlgrithmInterface();
	}
}
//具体策略类A
class ConcreteStrategyA implements Strategy {

	public void AlgrithmInterface() {
		System.out.println("ConcreteStrategyA.AlgrithmInterface");
	}
}
//具体策略类B
class ConcreteStrategyB implements Strategy {
	public void AlgrithmInterface() {
		System.out.println("ConcreteStrategyB.AlgrithmInterface");
	}
}

public class Client {
	public static void main(String[] args) {
		//多重条件语句不易维护，而使用策略模式可以避免使用多重条件语句(if-else)
		//策略模式的重心不是如何实现算法，而是如何组织这些算法
		
		//当实现某一个功能存在多种算法或者策略，我们可以根据环境或者条件的不同选择不同的算法或者策略来完成该功能
		
		//如果使用多重条件转移语句实现（即硬编码），不但使条件语句变得很复杂，而且增加、删除或更换算法要修改原代码，不易维护，违背开闭原则。如果采用策略模式就能很好解决该问题。
		
		Strategy stgA = new ConcreteStrategyA();
		Context ct = new Context(stgA);
		ct.DoAction();

		// JDK示例
		//容器布局管理就是一个典型的实例，Java SE 中的每个容器都存在多种布局供用户选择
		Comparator comp = null;
		List list = null;
		Collections.sort(list, comp);// comp这个是接口,可以有不同的实现

	}

}