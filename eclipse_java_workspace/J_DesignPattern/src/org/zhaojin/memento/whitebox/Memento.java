package org.zhaojin.memento.whitebox;

public class Memento //备 忘Orignator的状态
{
    private String state;

    public Memento(String state)
    {
        this.state = state;
    }

    public String getState()//应该只可让Orininator来仿问
    {
        return this.state;
    }

    public void setState(String state)//应该只可让Orininator来仿问
    {
        this.state = state;
    }
}
