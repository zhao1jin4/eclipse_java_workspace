package chapter3.aop.aspectj.introduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import junit.framework.TestCase;

public class ProductTest extends TestCase {
	private static final String TEST_FAILURE_MSG = "测试失败";
	/**
	 * 测试商品是否待售
	 */
	public void testIsOnSale() {
		Product product = new Product();
		product.setName("A");
		product.setPrice(1000);
		assertEquals(errorMsgTitle("商品待售"), true,
				((IValidatable)product).isOnSale());
	}

	public void testIsNotOnSale() {
		Product product = new Product();
		assertEquals(errorMsgTitle("商品非待售"), false,
				((IValidatable)product).isOnSale());
	}
	public void testE()
	{
		String x=new String("b");
		
		assertTrue("b"==x);
	}

	/**
	 * 测试商品是否按价格升序排序
	 */
	public void testProductSortedAsc() {
		List productList = new ArrayList();
		Product product1 = new Product();
		product1.setName("A");
		product1.setPrice(1000);
		Product product2 = new Product();
		product2.setName("B");
		product2.setPrice(100);
		Product product3 = new Product();
		product3.setName("C");
		product3.setPrice(500);

		productList.add(product1);
		productList.add(product2);
		productList.add(product3);

		/*
		 * 对productList进行排序
		 *
		 * 只有当productList中的元素具有可比性的时候，sort操作才会有意义
		 * 即Product元素必须实现Comparable接口
		 */
		Collections.sort(productList);
		int[] prices = new int[3];
		for (ListIterator iter = productList.listIterator(); iter.hasNext();) {
			Product product = (Product) iter.next();
			int currentIndex = iter.nextIndex() - 1;
			prices[currentIndex] = product.getPrice();
			System.out.println(currentIndex + ":" + product);
		}

		assertTrue(errorMsgTitle("商品按价格升序排序"),
				(prices[0]<=prices[1] && prices[1]<=prices[2]));
	}

  /**
   * 依据传入的内容,显示测试失败的相应信息
   */
  private String errorMsgTitle(String msg) {
  	return msg + " " + TEST_FAILURE_MSG;
  }
}
