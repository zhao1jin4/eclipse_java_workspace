package chapter3.aop.aspectj.introduction;

public aspect ProductValidateAspect {
	declare parents: Product extends IValidatable;
	public boolean Product.isOnSale() {
		return this.getPrice() > 0 ?  true: false;
	}
}
