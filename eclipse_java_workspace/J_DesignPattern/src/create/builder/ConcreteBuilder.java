package create.builder;
public class ConcreteBuilder extends Builder
{
    public void buildPartA()
    {
        product.setPartA("建造 PartA");
    }
    public void buildPartB()
    {
        product.setPartA("建造 PartB");
    }
    public void buildPartC()
    {
        product.setPartA("建造 PartC");
    }
}