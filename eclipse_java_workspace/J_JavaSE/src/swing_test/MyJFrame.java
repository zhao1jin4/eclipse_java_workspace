package swing_test;
import javax.swing.*;
public class MyJFrame extends JFrame 
{
	
	
	public MyJFrame()
	{
		JFrame jf=new JFrame("myjframe");
		JButton jb=new JButton("buttionmy");
		
		jf.getContentPane().add(jb);
		jf.setVisible(true);
		
		Object[] options = { "OK", "CANCEL" };
		JOptionPane.showOptionDialog(null, "Click OK to continue", "Warning", 
		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		null, options, options[0]);
			jf.setSize(200,500);
	}


public static  void main(String args[])
{
	MyJFrame myf=new MyJFrame ();
	myf.setSize(500,620);
	myf.setVisible(true);
}

}
