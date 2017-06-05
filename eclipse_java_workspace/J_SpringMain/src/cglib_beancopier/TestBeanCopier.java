package cglib_beancopier;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;

class In{
	private String _id;
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
}

class Out{
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
public class TestBeanCopier 
{
	 private static ConcurrentHashMap<String,BeanCopier> cache=new ConcurrentHashMap<String, BeanCopier>();
	 public static <T> T copyBeanProperties(Object sourceObj, T target, boolean useConverter)
	 {
        if(sourceObj==null || target==null) 
        	return null;
        
        String key=sourceObj.getClass().getSimpleName()+target.getClass().getSimpleName();
        BeanCopier copier = cache.get(key);
        if(copier==null){
        	copier = BeanCopier.create(sourceObj.getClass(), target.getClass(), useConverter);
  	        cache.putIfAbsent(key, copier);
        }
        copier.copy(sourceObj, target, null);//是调用的getter/setter方法
        return target;
    }
	
	public static void main(String[] args) 
	{
		In in =new In();
		in.setId("12312312");
		
		Out out=new Out();
		
		copyBeanProperties(in,out,false);
		System.out.println(out.getId());
	}

}
