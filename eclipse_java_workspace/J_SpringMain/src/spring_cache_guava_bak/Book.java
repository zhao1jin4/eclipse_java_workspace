package spring_cache_guava_bak;

import java.io.Serializable;
//guava  可以不要 Serializable ,public 可以不要getter/setter/toString
//redis要的 Serializable
public class Book  implements Serializable
{
	public String bookName;

	
//	private static final long serialVersionUID = 1L;
//	
//	public String getBookName() {
//		return bookName;
//	}
//
//	public void setBookName(String bookName) {
//		this.bookName = bookName;
//	}
//
//	@Override
//	public String toString() {
//		return "Book [bookName=" + bookName + "]";
//	}
//	
	
}
