package redis_redisson;


public class AnyObject // 可以不用  implements Serializable
{
	 
	//要有属性
	private int id;
	private String name;

	public AnyObject() {
		this.id=123;
		this.name="name12321";
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}