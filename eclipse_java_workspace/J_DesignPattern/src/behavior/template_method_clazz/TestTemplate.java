package behavior.template_method_clazz;

public class TestTemplate {
	//HttpServle 的service 调用doGet,doPost
	//命名规范 模板方法中留给子类实现的方法都以do开头
	
}
abstract class AbstractMethod 
{
	  protected abstract void method1();
	  protected abstract void method2();

	  // 模板方法，统一调用上面两个在子类中会被实现的方法，即不同的实现
	  public final void templateMethod() {
	    /*
	     * 此方法体内也可以通过“钩子方法”来实现根据一定情况调用 不同的方法组合
	     */
	    if (hookMethod()) {
	      method1();
	    }else {
	      method1();
	      method2();
	    }    
	  } 
	// 钩子方法,protected权限可被子类覆盖，默认为返回true
	  protected boolean hookMethod() {
	    return true;
	  } 
}