package cglib_beancopier;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;

class In{
	private String _id;
	private String in;
	private Date birthday;
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
}

class Out{
	private String id;
	private String out;
	private Date birthday;
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
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
        copier.copy(sourceObj, target, null);//是调用的getter/setter方法,双方不同类都有匹配不上的字段也可正常用
      //网上查cglib 的BeanCopier 性能还不错 
        return target;
    }
	
	public static void main(String[] args) 
	{
		In in =new In();
		in.setId("12312312");
		in.setBirthday(new Date());
		Out out=new Out();
		
		copyBeanProperties(in,out,false);
		System.out.println(out.getId()+"=="+out.getBirthday());
	}

}
