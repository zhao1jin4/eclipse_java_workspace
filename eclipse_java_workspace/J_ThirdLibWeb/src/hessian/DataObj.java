package hessian;

import java.io.Serializable;

public class DataObj implements  Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String dataId;
	
	private String desc; //测试 有相同的属性

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
