package org.zhaojin.memento.whitebox;

public class Caretaker //只负责保存备忘录对象,不可仿问对象内部
{
    /**
     * @link aggregation 
     * @label wide
     */
    private Memento memento;//这里危险是可以仿问对象内部,所以叫白箱实现

    public Memento retrieveMemento()
    {
        return this.memento;
    }

    public void saveMemento(Memento memento)
    {
     	this.memento = memento;
    }
}
