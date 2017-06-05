package thread.interupted;
public class Writer extends Thread {      
       
    private BufferInterruptibly buff;      
       
    public Writer(BufferInterruptibly buff) {      
        this.buff = buff;      
    }      
       
    @Override      
    public void run() {      
        buff.write();      
    }      
       
}      
     