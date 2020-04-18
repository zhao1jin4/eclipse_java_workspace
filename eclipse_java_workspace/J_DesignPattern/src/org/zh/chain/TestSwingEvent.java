package org.zh.chain;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
//是观察者模式
public class TestSwingEvent extends JFrame   
{
	JButton button=new JButton("button");
	JPanel panel=new JPanel();
	 public TestSwingEvent()
	 {
		 MyAdater my1=new MyAdater( );
		 MyAdater my2=new MyAdater( );
		 MyAdater my3=new MyAdater( );
		 
		 button.addMouseListener(my1);
		 panel.addMouseListener(my2);
		 this.addMouseListener(my3);
		 
		 
		 
		 panel.add(button);
		 this.add(panel);
		 
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 this.pack();
		
		 
	 }
	public static void main(String[] args) {
		new TestSwingEvent();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
class MyAdater extends MouseAdapter
{
	public void mouseClicked(MouseEvent e) {
		JComponent comp=(JComponent)e.getSource();
		
		//comp.getParent().dispatchEvent(e);//容易 StackOverflowError
		
		System.out.println(e.getSource().getClass().getName());
		
	}
	
}
