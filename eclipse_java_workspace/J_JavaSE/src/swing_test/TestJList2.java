package swing_test;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class TestJList2 {
	public static void main(String args[]) {
		String items[] = {"One", "Two", "Three"};
		JList list = new JList(items); 
		ListSelectionModel sModel = list.getSelectionModel();		
		sModel.addListSelectionListener (new ListSelectionListener() { 
		        public void valueChanged(ListSelectionEvent e) { 
			System.out.println("selection changed: " + e.getLastIndex()); 
		        } 
		}); 		
		JFrame f = new JFrame();
		f.getContentPane().add(list);
		f.setSize(300,400);
		f.setVisible(true);		
	}
}
