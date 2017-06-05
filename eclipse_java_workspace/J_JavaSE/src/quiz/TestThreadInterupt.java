package quiz;
public class TestThreadInterupt
{
    public static void main (String[] args)
    {
        final Thread mainThread = Thread.currentThread();

        Thread anotherThread = new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mainThread.interrupt();
            }
        });

        anotherThread.start();

        try {
            Thread.sleep(2000);
            System.out.println("Thread execution finished!");
        } catch (InterruptedException ie_) {
            System.out.println("Exception caught!");
        }
    }
}
