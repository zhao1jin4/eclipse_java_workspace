package myservlet.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueLabelBean {
	private int value;
	private String label;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public static void main(String[] args) {
		List<Map<String,String>> dataSet=new ArrayList<>();
		for(int i=0;i<5;i++)
		{
			Map<String,String> row =new HashMap<>();
			row.put("username", "lisi_"+i);
			row.put("password", "123_"+i);
			row.put("age", "10__"+i);
			dataSet.add(row);
		}
	}
}
