package nio;
import java.io.IOException;  
import java.io.PipedInputStream;  
import java.io.PipedOutputStream;  
  
public class PipedStreamTest {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        Producer p = new Producer();  
        Consumer c = new Consumer();  
        Thread t1 = new Thread(p);  
        Thread t2 = new Thread(c);  
        try {  
            p.getPipedOutputStream().connect(c.getPipedInputStream());  
            t2.start();  
            t1.start();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    private static class Producer implements Runnable {  
        private PipedOutputStream pos;  
  
        public Producer() {  
            pos = new PipedOutputStream();  
        }  
  
        public PipedOutputStream getPipedOutputStream() {  
            return pos;  
        }  
  
        @Override  
        public void run() {  
            try {  
                for (int i = 1; i <= 10; i++) {  
                    pos.write(("This is a testing message, messageId=" + i + "\n").getBytes());  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    pos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    private static class Consumer implements Runnable {  
        private PipedInputStream pis;  
  
        public Consumer() {  
            pis = new PipedInputStream();  
        }  
  
        public PipedInputStream getPipedInputStream() {  
            return pis;  
        }  
  
        @Override  
        public void run() {  
            int len = -1;  
            byte[] buffer = new byte[1024];  
            try {  
                //若没有数据可读,则让读进程等待(见read()函数)  
                while ((len = pis.read(buffer)) != -1) {  
                    System.out.println(new String(buffer, 0, len));  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }finally{  
                try {  
                    pis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
  
    }  
  
}  