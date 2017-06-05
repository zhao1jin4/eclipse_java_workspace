package swing_test;
	import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class TestJList {
	public static void main(String args[]) {
		String items[] = {"One", "Two", "Three"};
		JList list = new JList(items); 
		String[] ll={new String ("aa"),new String ("bb"),new String ("cc")};
		
		SpinnerNumberModel  model=new SpinnerNumberModel(60,1,100,2);
//			SpinnerNumberModel  model=new SpinnerNumberModel();
		JSpinner spinner=new JSpinner(model);
	//model.setValue("10");
JProgressBar  progress =new JProgressBar(JProgressBar.HORIZONTAL,10,100);
progress.setStringPainted(true);






		ListSelectionModel sModel = list.getSelectionModel();		
		sModel.addListSelectionListener (new ListSelectionListener() { 
		        public void valueChanged(ListSelectionEvent e) { 
			System.out.println("selection changed: " + e.getLastIndex()); 
		        } 
		}); 		
		JFrame f = new JFrame();
		//f.getContentPane().add(list);
		

		f.setLayout(new BorderLayout());
		f.getContentPane().add(spinner,BorderLayout.NORTH);
		f.getContentPane().add(progress);

		f.setSize(300,400);
		f.setVisible(true);		
		while(true)
progress.setValue(((Integer)spinner.getValue()).intValue()) ;
	}
}
