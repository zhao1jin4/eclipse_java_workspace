package easymock;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesOrderImpl implements SalesOrder
{
	String orderNumber;
	double totalPrice;
  public void loadDataFromDB(ResultSet resultSet) throws SQLException
  {
	    orderNumber = resultSet.getString(1);
	    totalPrice = resultSet.getDouble(2);
  }
	public String getPriceLevel() {
		String[] priceLevels = { "Level_A", "Level_B", "Level_C" };
		  if(totalPrice>100 && totalPrice<=1000)
			  return priceLevels[0];
		  else  if(totalPrice>1000 && totalPrice<=10000)
			  return priceLevels[1];
		  else if(totalPrice>10000)
			  return priceLevels[2];
		return null;
	}
	@Override
	public float  getProductPrice(String product) {
		if("orange".equals(product))
			return 20.0f;
		else if("apple".equals(product))
			return 15.0f;
		else
			return 0.0f;
	}
}