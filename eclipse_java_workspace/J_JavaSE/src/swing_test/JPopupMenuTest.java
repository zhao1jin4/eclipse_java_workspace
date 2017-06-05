package swing_test;
import javax.swing.*;
public class  JPopupMenuTest
{
	public static void main(String[] args) 
	{
		JFrame frame=new JFrame();
		JButton button=new JButton("popupMenu");
		JPopupMenu popupmenu=new JPopupMenu( );
		JMenuItem one=new JMenuItem("first");
		JMenuItem two=new JMenuItem("second");
		popupmenu.add(one); //, icon
		
		popupmenu.add(two);
		popupmenu.add(new JMenuItem("third"));

JPanel panel=new JPanel();


panel.setComponentPopupMenu(popupmenu);

		button.setComponentPopupMenu(popupmenu);
		frame.add(panel);
		frame.setSize(300,300);
		frame.setVisible(true);
	}
}
