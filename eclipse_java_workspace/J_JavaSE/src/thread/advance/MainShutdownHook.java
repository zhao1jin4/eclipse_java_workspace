package thread.advance;

public class MainShutdownHook {

	public static void main(String[] args) {
		Thread shutdownHook = new Thread() {
			@Override
			public void run() {
				 System.out.println("before JVM exits ,do something..");
			}
		};
		Runtime.getRuntime().addShutdownHook( shutdownHook);
		
		System.out.println("last..");
	}

}
