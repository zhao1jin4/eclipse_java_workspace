package org.zh.iterator;

import java.util.ArrayList;
import java.util.List;

public class TestIterator {
	public static void main(String[] args) {
		//List  的listIterator方法返回 ListIterator 可以双向的移动
		//Enumeration 是老的,没有remove方法
		
		//黑箱聚集,自身存游标
		//白箱聚集,自身不存游标,
		
		ArrayList list =new ArrayList();
		list.iterator();
		list.listIterator();
		
	}

}
