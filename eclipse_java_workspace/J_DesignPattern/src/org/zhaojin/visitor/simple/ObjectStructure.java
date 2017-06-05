package org.zhaojin.visitor.simple;
import java.util.Vector;
import java.util.Enumeration;

public class ObjectStructure
{
    private Vector nodes;

    /**
     * @link aggregation 
     */
    private Node node;

    public ObjectStructure()
    {
        nodes = new Vector();
    }

    public void action(Visitor visitor)
    {
        for(Enumeration e = nodes.elements();
        	e.hasMoreElements();)//Iterator模式
        {
            node = (Node) e.nextElement();
            node.accept(visitor);//这里的集合中的类是不一样的每个都自已专有的方法
        }
    }

    public void add(Node node)
    {
        nodes.addElement(node);
    }
}
