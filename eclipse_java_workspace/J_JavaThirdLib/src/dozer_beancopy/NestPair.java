package dozer_beancopy;

public class NestPair {
	private Object first;
	private Object second;
	//必须默认构造器
	public NestPair() {
		
	}
	public NestPair(Object first, Object second) {
		super();
		this.first = first;
		this.second = second;
	}
	public Object getFirst() {
		return first;
	}
	public void setFirst(Object first) {
		this.first = first;
	}
	public Object getSecond() {
		return second;
	}
	public void setSecond(Object second) {
		this.second = second;
	}
	@Override
	public String toString() {
		return "NestPair [first=" + first + ", second=" + second + "]";
	}
	
	
}
