package behavior.state;

public class TestState {
	 //避免 if-else 语句来做状态判断
	//JDK 中 多线程存在 6种状态，分别为新建状态、运行状态、阻塞状态(WAITING,TIMED_WAITING,BLOCKED,)和死亡状态
	
	//和策略的区别,
	//策略中一旦环境角色选择了一个具体策略类,不会改变
	//状态,环境角色有明显示的状态转移,有不同的状态对象,环境类是被外在放进一个具体的状态
	public static void main(String[] args) {
		Thread.State x=Thread.State.RUNNABLE;
	}

}
