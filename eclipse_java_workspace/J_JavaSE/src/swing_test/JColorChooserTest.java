package swing_test;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
public class JColorChooserTest
{
	public static void main(String[] args) 
	{
		    JFrame.setDefaultLookAndFeelDecorated(true);
			

		final	JFrame frame=new JFrame();
		final DefaultColorSelectionModel model=new DefaultColorSelectionModel();
		final JColorChooser colorchooser=new JColorChooser(model);
	
			
					model.addChangeListener(new ChangeListener()
					{
						public void stateChanged(ChangeEvent e)
						{
							//DefaultColorSelectionModel de=(DefaultColorSelectionModel)e.getSource();
							//System.out.println(de.getSelectedColor());
							Color co=(Color)e.getSource();
							
							System.out.println(co.toString());
						}
				
					});

		

			JButton button=new JButton("choose color");
			button.addActionListener(new ActionListener()
						{	public void actionPerformed(ActionEvent e)
							{
								Color col=colorchooser.showDialog(frame, "please select color", Color.RED) ;

								System.out.println(col.toString());

							}

							
						});
							JDialog.setDefaultLookAndFeelDecorated(true);

		frame.add(button);
						
			frame.setSize(300,300);
			frame.setVisible(true);
	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//DISPOSE_ON_CLOSE
			//frame.dispose();
	}
}
