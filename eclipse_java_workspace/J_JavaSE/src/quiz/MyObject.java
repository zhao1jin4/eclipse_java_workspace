package quiz;

public class MyObject // for TestHashCode 
{
	private int id;
	private String name;

	public static int N=1;//test for classLoader
	
	
	public MyObject() {
	}

	
	public MyObject(int id, String name) {
		this.id = id;
		this.name = name;

	}
	public String toString()
	{
		return id+":"+name;
	}
	public int hashCode() 
	{
		int res= (1000* this.id) ;
		if(name!=null)
			res+= name.toCharArray().length;
		return res;
	}
	/*
	public boolean equals(Object obj) {
		if (obj instanceof MyObject )
		{
			MyObject t=(MyObject)obj;
			if( t.id==this.id )
				if(t.name == null && this.name==null )
					return true;
				else if (t.name!=null && t.name.equals(this.name) ) 
					return true;
		}
		return false;
	}
	 */
  
}



abstract class AbstractClass
{
	public AbstractClass()
	{
		
	}
}