package easymock;

import org.easymock.IArgumentMatcher;

public class SQLEquals implements IArgumentMatcher
{
  private String expectedSQL = null;
  public SQLEquals(String expectedSQL)
  {
    this.expectedSQL = expectedSQL;
  }
  
  @Override
  public boolean matches(Object actualSQL) 
  {
    if (actualSQL == null && expectedSQL == null)
      return true;
    else if (actualSQL instanceof String)
      return expectedSQL.equalsIgnoreCase((String) actualSQL);
    else
      return false;
  }
	@Override
	public void appendTo(StringBuffer stringbuffer) {
	}
}