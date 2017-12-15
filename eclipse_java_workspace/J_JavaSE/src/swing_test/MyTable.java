package swing_test;
import javax.swing.*;
import java.util.Vector;
import javax.swing.table.*;
import java.awt.*;

public class MyTable extends JFrame 

{
	JTable mytable=null;
	//Vector v=new Vector();
	DefaultTableModel model=null;
//JTableHeader header=new JTableHeader();
	public MyTable(String title)
	{super(title);
		
		//v.add("id");
	//	v.add("name");
	//	v.add("gender");
	Object[] o={"id","name","gender"};
		DefaultTableModel model=new DefaultTableModel(o,0);
		
		Object[][] values=new Object[][]{{"1","lisi","man"},{"2","wnagwu","man"},{"3","lili","women"}};
		
		
		model.addRow(values[0]);
		model.addRow(values[1]);
		model.addRow(values[2]);
		
		mytable=new JTable(model);
		this.add(mytable);
		this.add(mytable.getTableHeader(),BorderLayout.NORTH);
		
	
	
	}
	public static void main(String args[])
	{
		MyTable table=new MyTable("mytable");
		table.setSize(200,500);
		table.setVisible(true);
		
	}

}
