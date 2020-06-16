package behavior.mediator.demo;

public class Colleague2 extends Colleague
{
	public Colleague2(Mediator m)
	{
		super( m );
	}

    public void action()
    {
        System.out.println("This is an action from Colleague 2");
    }
}

