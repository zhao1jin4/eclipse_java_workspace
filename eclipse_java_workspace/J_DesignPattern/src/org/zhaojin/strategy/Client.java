package org.zhaojin.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Context {

	private Strategy stg;

	public Context(Strategy theStg) {
		this.stg = theStg;
	}

	public void DoAction() {
		this.stg.AlgrithmInterface();
	}
}

class ConcreteStrategyA implements Strategy {

	public void AlgrithmInterface() {
		System.out.println("ConcreteStrategyA.AlgrithmInterface");
	}
}

class ConcreteStrategyB implements Strategy {
	public void AlgrithmInterface() {
		System.out.println("ConcreteStrategyB.AlgrithmInterface");
	}
}

public class Client {
	public static void main(String[] args) {
		Strategy stgA = new ConcreteStrategyA();
		Context ct = new Context(stgA);
		ct.DoAction();

		// JDK示例
		Comparator comp = null;
		List list = null;
		Collections.sort(list, comp);// comp这个是接口,可以有不同的实现

	}

}