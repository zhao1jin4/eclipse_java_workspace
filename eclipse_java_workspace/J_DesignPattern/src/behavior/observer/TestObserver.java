package behavior.observer;

import java.util.Observable;
import java.util.Observer;


class Product extends Observable //-----JDK是class 
{
	private String name;// //产品名
	private float price;// //价格

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//设置变化点
		this.setChanged();//-----JDK
		this.notifyObservers(name);//-----JDK //通知观察者
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		// //设置变化点
		setChanged();
		notifyObservers(new Float(price));

	}

	// //以下可以是数据库更新 插入命令.
	public void saveToDb() {
		System.out.println("saveToDb");
	}

}

class NameObserver implements Observer  //-----JDK
{
	private String name = null;
	public void update(Observable obj, Object arg) 
	{
		if (arg instanceof String) {
			name = (String) arg;
			// //产品名称改变值在name中
			System.out.println("NameObserver :name changet to " + name);
		}
	}
}

class PriceObserver implements Observer 
{
	private float price = 0;

	public void update(Observable obj, Object arg)
	{
		if (arg instanceof Float) 
		{

			price = ((Float) arg).floatValue();

			System.out.println("PriceObserver :price changet to " + price);
		}
	}
}
public class TestObserver 
{

	public static void main(String args[]) 
	{
		//JDK中的示例　addMouseListener
		//SAX2 解析XML ,addEventHandler(  ContentHandler 或者DefaultHandler)

		
		
		
		Product product = new Product();
		NameObserver nameobs = new NameObserver();
		PriceObserver priceobs = new PriceObserver();

		// //加入观察者
		product.addObserver(nameobs);//-----JDK
		product.addObserver(priceobs);

		product.setName("applet");
		product.setPrice(9.22f);

	}
}
