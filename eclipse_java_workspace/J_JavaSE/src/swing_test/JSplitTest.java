package swing_test;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class JSplitTest
{
	public static void main(String[] args) 
	{
		Object[ ] obj={new String ("first"),new String ("second"),new String("thrid")};
		DefaultComboBoxModel model=new DefaultComboBoxModel();
		
		Vector vector=new Vector();
		vector.add("one");
		vector.add("tow");
			vector.add("threee");
		final JComboBox combo=new JComboBox(vector);
		JFrame frame=new JFrame("JComboBoxt Test");
			
			final JTextField text=new JTextField(30);
			JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,text,combo);


	
  //      split.setResizeWeight(0.5);
      //  split.setOneTouchExpandable(true);
      //  split.setContinuousLayout(true);




JButton but=new JButton("button this ");

			frame.add(split,"Center");
		//	frame.add(text,"East");
	//frame.add(combo,"West");
			

combo.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e)
	{
							JComboBox mycom=(JComboBox)e.getSource();
						text.setText( combo.getSelectedItem().toString()  ) ;
	}

							});
	
	



		frame.setSize(500,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	frame.setDefaultLookAndFeelDecorated(true);
	
		frame.setVisible(true);






	}
}
