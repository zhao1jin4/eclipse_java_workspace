package voltile_test;
public class Test1 {

    private static boolean flag = true;//û�м�volatile�ؼ��ֵĳ�Ա�������߳�֮�䲻�ɼ�

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag) {
                i++;
                //System.out.println("i=" + i);
            }
            System.out.printf("**********test1 �����ɹ�, i=%d **********\n", i);
        });
        thread.start();
        Thread.sleep(100);
        flag = false;
        System.out.printf("**********test1 main thread ����, i=%d **********\n", i);
        //Ϊ�����߳̽��������̻߳������У���
    }
}