package easymock;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SalesOrder
{
  public void loadDataFromDB(ResultSet resultSet) throws SQLException;	
  public String getPriceLevel();
  public float getProductPrice(String product);
}