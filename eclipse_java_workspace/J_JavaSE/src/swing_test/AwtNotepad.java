package swing_test;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public  class AwtNotepad 
{
	Frame f=new Frame();
	TextArea text=new TextArea();
	MenuBar menu=new MenuBar();
	Menu file=new Menu("file");
	Menu edit=new Menu("edit");
	MenuItem format=new MenuItem("format ");
	MenuItem open=new MenuItem("open");
	MenuItem exit=new MenuItem("exit");
	
	public AwtNotepad()
	{
		//super(title);
		
		file.add(open);
		file.add(exit);
		edit.add(format);
	
		
		f.setMenuBar(menu);
		f.add(text,"South");
		//public void actionPerFormed(ActionEvent e)

		f.addWindowListener( new WindowAdapter()	 
								{
									 public void windowClosing(WindowEvent e)
									{
										System.exit(0);
									}
								});
	
		f.setLocation(500,500);
		f.setSize(200,200);
		f.setVisible(true);
	
	
	
	}

	 			

	
	public static void main(String[] args) 
	{
		AwtNotepad notepad=new AwtNotepad();
	
		

	}

}
