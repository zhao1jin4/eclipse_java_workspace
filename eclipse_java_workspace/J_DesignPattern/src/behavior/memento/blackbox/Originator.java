package behavior.memento.blackbox;

public class Originator
{
    public Originator()
    {
    }

    private String state;

    /** @link dependency 
     * @label wide*/
    /*#MementoIF lnkMementoIF;*/

	public MementoIF createMemento()
	{
		return new Memento( this.state );//是MementoIF的没有方法,就算强转为Memento,也不能仿问private
	}
	
	public void restoreMemento( MementoIF memento)
	{
        Memento aMemento = (Memento) memento;

		this.setState( aMemento.getState() );//这里可仿问private方法
	}

    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
    {
        this.state = state;
        System.out.println("state = " + state);
    }
	
	class Memento implements MementoIF //用于保存Originator中的属性,是内部类,全是private的,只有外部类可以仿问
	{
		private String savedState;
		
		private Memento(String someState)
		{
			savedState = someState;
		}
		
		private void setState(String someState)
		{
			savedState = someState;
		}

		private String getState()
		{
			return savedState;
        }
	}
}
