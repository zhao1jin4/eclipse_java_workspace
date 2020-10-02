package apache_camel;

import java.util.List;

public class ResultHandler {
	public void processResult(List  list)
	{
		for( Object item: list)
			System.out.println(item);
	}
}
