package struct.composite;

import java.util.ArrayList;
public class CompositePattern
{
	//在需要表示一个对象整体与部分的层次结构的场合
	//将对象组合成树状的层次结构的模式
	//组合模式分为透明式的组合模式和安全式的组合模式。
	//透明方式：在该方式中，由于抽象构件声明了所有子类中的全部方法,客户端无须区别树叶对象和树枝对象，对客户端来说是透明的	
	//安全方式：在该方式中，将管理子构件的方法移到树枝构件中，抽象构件和树叶构件没有对子对象的管理方法,失去了透明性
    public static void main(String[] args)
    {
        Component c0=new Composite(); 
        Component c1=new Composite(); 
        Component leaf1=new Leaf("1"); 
        Component leaf2=new Leaf("2"); 
        Component leaf3=new Leaf("3");          
        c0.add(leaf1); 
        c0.add(c1);
        c1.add(leaf2); 
        c1.add(leaf3);          
        c0.operation(); 
    }
}
//抽象构件
interface Component
{
    public void add(Component c);
    public void remove(Component c);
    public Component getChild(int i);
    public void operation();
}
//树叶构件
class Leaf implements Component
{
    private String name;
    public Leaf(String name)
    {
        this.name=name;
    }
    public void add(Component c){ }           
    public void remove(Component c){ }   
    public Component getChild(int i)
    {
        return null;
    }   
    public void operation()
    {
        System.out.println("树叶"+name+"：被访问！"); 
    }
}
//树枝构件
class Composite implements Component
{
    private ArrayList<Component> children=new ArrayList<Component>();   
    public void add(Component c)
    {
        children.add(c);
    }   
    public void remove(Component c)
    {
        children.remove(c);
    }   
    public Component getChild(int i)
    {
        return children.get(i);
    }   
    public void operation()
    {
        for(Object obj:children)
        {
            ((Component)obj).operation();
        }
    }    
}