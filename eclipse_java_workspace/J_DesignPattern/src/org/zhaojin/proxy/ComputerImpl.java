package org.zhaojin.proxy;

public class ComputerImpl implements Computer {
	private int price;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Object buy(String price) {
		System.out.println("computer is bufy price is " + price);
		return "SUCESS";

	}
}
