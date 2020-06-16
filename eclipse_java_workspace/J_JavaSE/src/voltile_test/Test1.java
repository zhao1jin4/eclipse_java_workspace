package voltile_test;
public class Test1 {

    private static boolean flag = true;//没有加volatile关键字的成员变量多线程之间不可见

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag) {
                i++;
                //System.out.println("i=" + i);
            }
            System.out.printf("**********test1 跳出成功, i=%d **********\n", i);
        });
        thread.start();
        Thread.sleep(100);
        flag = false;
        System.out.printf("**********test1 main thread 结束, i=%d **********\n", i);
        //为何主线程结束，子线程还在运行？？
    }
}