package my_code.condition;

public class Account {
	private String name;	 //哈尔滨银行，浦发银行
	private String status;   //yes表示可用，no表示不可用
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
