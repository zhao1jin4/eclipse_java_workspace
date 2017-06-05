package chapter3.aop.aspectj.introduction;
//引入Product类没有implements Comparable接口
public aspect ProductComparableAspect
{
	declare parents: Product extends Comparable;
	public int Product.compareTo(Object o)
	{
		return (int) (this.getPrice() - ((Product) o).getPrice());
	}
}