package thrift.hello; 
 import org.apache.thrift.async.AsyncMethodCallback; 

 public class MethodCallback implements AsyncMethodCallback // <String> 
 { 
	 Object response = null; 

    public Object getResult() { 
        // ���ؽ��ֵ
        return this.response; 
    } 

    // ������񷵻صĽ��ֵ
    @Override 
    public void onComplete(Object response) { 
        this.response = response; 
    } 
    // ������÷�������г��ֵ��쳣
	@Override
	public void onError(Exception arg0) {
		
	} 
 }