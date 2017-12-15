package cxf_video.xml;

public class AddressImpl implements Address {

	private String street="南京路";
	private int  no=30;
	
	public AddressImpl(String street, int no) {
		super();
		this.street = street;
		this.no = no;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
}
