package org.zh.memento.blackbox;

public class Caretaker
{
    /**
     * @link aggregation 
     * @label narrow
     */
	
	//只负责保存对象,可以保存多个,不能仿问方法,没有对外仿问的方法
    private MementoIF memento;

    public MementoIF retrieveMemento()
    {
    	
        return this.memento;
    }

    public void saveMemento(MementoIF memento)
    {
     	this.memento = memento;
    }
}
