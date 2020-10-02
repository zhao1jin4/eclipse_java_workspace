package behavior.visitor.simple;

//也可有多个Visitor
public class Visitor
{
    public void visit(NodeA nodeA)//可是多个不同的方法名
    {
        System.out.println( nodeA.operationA() );//必须知道每个专有方法，分离到外面来调用，而不是在遍历时判断来调用
    }
    public void visit(NodeB nodeB)
    {
        System.out.println( nodeB.operationB() );
    }
}
