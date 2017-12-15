package org.zhaojin.visitor.simple;

public class NodeB extends Node
{
	//调用它的最终目标是调用operationB,如不要Visitor,直接调用方法,就是策略
	public void accept(Visitor visitor)
    {
        visitor.visit(this);//可是多个不同的方法名,this的特殊之处　,　Visitor传给NodeB来调用我
        //放在父类中就没有专有方法(operationB)可用
    }

    public String operationB()
    {
       return "NodeB is visited";
    }
}
