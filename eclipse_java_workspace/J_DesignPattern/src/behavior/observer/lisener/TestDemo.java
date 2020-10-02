package behavior.observer.lisener;

class DemoListener1 implements DemoListener {
	public void handleEvent(DemoEvent de) {
		System.out.println("Inside listener1...");
		de.say();// 回调
	}
}

public class TestDemo {
	DemoSource ds;

	public TestDemo() {
		try {
			ds = new DemoSource();

			// 将监听器在事件源对象中登记：
			DemoListener1 l1 = new DemoListener1();
			ds.addDemoListener(l1);
			ds.addDemoListener(new DemoListener() {
				public void handleEvent(DemoEvent event) {
					System.out.println("Method come from 匿名类...");
				}
			});

			ds.notifyDemoEvent();// 触发事件、通知监听器

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new TestDemo();
	}
}