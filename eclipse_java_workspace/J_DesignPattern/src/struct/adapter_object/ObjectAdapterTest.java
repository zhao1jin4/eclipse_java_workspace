package struct.adapter_object;

//适用于
//以前开发的系统存在满足新系统功能需求的类，但其接口同新系统的接口不一致。
//使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。

//目标接口
interface Target
{
  public void request();
}

//适配者接口
class Adaptee
{
  public void specificRequest()
  {       
      System.out.println("适配者中的业务代码被调用！");
  }
}



//对象适配器类
class ObjectAdapter implements Target
{
    private Adaptee adaptee;
    public ObjectAdapter(Adaptee adaptee)
    {
        this.adaptee=adaptee;
    }
    public void request()
    {
        adaptee.specificRequest();
    }
}
//客户端代码
public class ObjectAdapterTest
{
    public static void main(String[] args)
    {
        System.out.println("对象适配器模式测试：");
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request();
    }
}
