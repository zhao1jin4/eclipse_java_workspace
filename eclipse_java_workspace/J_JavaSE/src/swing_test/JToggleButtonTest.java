package swing_test;
import javax.swing.*;
import java.awt.event.*;
public class  JToggleButtonTest
{
	public static void main(String[] args) 
	{
		

   JFrame.setDefaultLookAndFeelDecorated(true);
   JFrame frame =new JFrame("Œ“µƒ–°≤‚ ‘ ");

		JToggleButton button1=new JToggleButton("first");
		JToggleButton button2=new JToggleButton("second");//selected
		JToggleButton button3=new JToggleButton("third");
		
		JPanel panel =new JPanel();

		ButtonGroup group =new ButtonGroup();

		
		 class FF implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
			System.out.println(	e.getActionCommand()+"is selected");
			}

		}
		FF ff=new FF();
		JButton html=new JButton("<html><center><b><u>E</u>nable</b><br>"
                 + "<font color=#ff0000>middle button</font>"
                 );

		button1.addActionListener(ff);
		button2.addActionListener(ff);
		button3.addActionListener(ff);

		group.add(button1);
		group.add(button2);
		group.add(button3);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(html);

		frame.add(panel);
	
		
			 

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);


	}
}
