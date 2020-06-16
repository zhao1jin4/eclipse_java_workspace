package struct.flyweight;

public class TestFlyWeight {
	public static void main(String[] args) {
		//如String类,创始后是被共享的
		//如字符(母) 在不同的字体下显示不同,但字符(母)是共享的
		//必须通过工厂,产生享元对象
		
		//单纯享元可以共享,复合享元不能被共享
		//内部状态（Internal State）和外部状态（External State）
		
		
//		运用共享技术来有効地支持大量细粒度对象的复用
//		享元模式的主要优点是：相同对象只要保存一份，这降低了系统中对象的数量，从而降低了系统中细粒度对象给内存带来的压力。
//		其主要缺点是： 为了使对象可以共享，需要将一些不能共享的状态外部化，这将增加程序的复杂性。
	}

}
