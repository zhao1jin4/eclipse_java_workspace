package swing_test;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class myJList extends JFrame implements ActionListener
{
	JList list=null;
	DefaultListModel model=new DefaultListModel();
	
	public myJList(String title)
	{
		super(title);
		
		model.add(0,"gwu");
		model.add(1,"lisis");
		model.add(2,"wangwu");
		model.add(3,"sis");
		model.add(4,"gwu");
		list=new JList(model);
		add(list,"North");
		JPanel p=new JPanel();
		JButton add=new JButton("add");
		JButton del=new JButton("delete");
		del.addActionListener(this);
		add.addActionListener(this);
		p.add(add);
		p.add(del);

				
		add(p,BorderLayout.CENTER);
		
	}
	
	
	public void actionPerformed(ActionEvent e)  
	{
		
		if(e.getActionCommand().equals("add"))
		{
			
				String values=JOptionPane.showInputDialog(this,"please input your intem");
				model.addElement(values);
				
		}
		else if (e.getActionCommand().equals("delete"))
				{
					if(list.getSelectedIndex()<0)
					{
						JOptionPane.showMessageDialog(this,"you hasn't select intem");
						
					}
					else{
						model.removeElementAt(list.getSelectedIndex());
					}
					
				
				}
		
		
	}
	public static void main(String args[])
	{
		myJList list=new myJList("myfistJLISt");
		list.setSize(500,720);
		list.setVisible(true);
		
	}
}
