package swing_test;
import java.awt.event.*;
import javax.swing.*;
public class Notepad extends JFrame 
{	JMenuBar menubar=new JMenuBar();
	JMenu file=new JMenu("File");
	JMenu edit=new JMenu("edit");
	JMenuItem newfile=new JMenuItem("new");
	JMenuItem open=new JMenuItem("open");
	JMenuItem copy=new JMenuItem("copy");
	JMenuItem exit=new JMenuItem("exit");

	JTextArea text=new JTextArea();
	JScrollPane scroll=new JScrollPane(text);
	public Notepad(String title )
	{	
		super(title);
		file.add(newfile);
		file.add(open);
		file.add(exit);
		edit.add(copy);
		menubar.add(file);
		menubar.add(edit);
		//text.setLineWrap(true);
		//text.setWrapStyleWord(true); 
		this.setJMenuBar(menubar);
		//menubar.setSize(500,100);
		//menubar.setVisible(true);
		//add(text,"Center");          //JTextPane and JEditorPane.  
		//JEditorPane editpane=new JEditorPane();
		scroll.updateUI();
		//scroll.add(text);
		
		//JViewport view=scroll.getViewport();
		//view.setOpaque(true);
		//view.setView(text);
		
		getContentPane().add(scroll);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

	public static void main(String args[])
	{
		Notepad myNotepad=new Notepad("这是我的第一个记事本");
		myNotepad.setSize(600,600);
		myNotepad.setVisible(true);
		
		
	}
	

}
