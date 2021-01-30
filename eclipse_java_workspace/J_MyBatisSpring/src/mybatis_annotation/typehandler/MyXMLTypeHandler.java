package mybatis_annotation.typehandler;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

//相当于 <typeHandlers> <typeHandler
//@MappedJdbcTypes(JdbcType.VARCHAR)
//@MappedTypes(List.class)

/* <typeHandlers >
	  <typeHandler  javaType="java.util.List" jdbcType="VARCHAR" 
	      	handler="mybatis_annotation.typehandler.MyXMLTypeHandler"/>  
	</typeHandlers>
*/
	
/*
<useSkills> 
 <name>JavaEE</name>
 <name>Oracle</name>
</useSkills> 
*/


public class MyXMLTypeHandler extends BaseTypeHandler<List<String>>
{
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType type) throws SQLException
	{
		if(parameter ==null)
		{
			ps.setString(i, null);
			return ;
		}
		StringBuffer result=new StringBuffer();
		result.append("<useSkills>");
		for(int k=0;k<parameter.size();k++)
		{
			result.append("<name>").append(parameter.get(k)).append("</name>");
		}
		result.append("</useSkills>");
		ps.setString(i, result.toString());
	}

	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException
	{
		 String xmlString=rs.getString(columnName);
		return parseXml2Object(xmlString);
	}

	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException
	{
		  String xmlString=rs.getString(columnIndex);
		  return parseXml2Object(xmlString);
	}

	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
	{
		  String xmlString= cs.getString(columnIndex);
		  return parseXml2Object(xmlString);
	}
	private List<String> parseXml2Object(String xmlString)
	{
		List<String>  result=new ArrayList<String>();
		
		SAXReader reader=new SAXReader();
		Document doc=null;
		try
		{
			StringReader strReader=new StringReader(xmlString);
			doc = reader.read(strReader);
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		Element root=doc.getRootElement();
		//System.out.println("根节点名："+root.getName());
		List<Element> names=root.elements();
		for(Iterator<Element> it= names.iterator();it.hasNext();)
		{
			Element name= it.next();
			result.add(name.getText());
		}
		return result;
	}
}