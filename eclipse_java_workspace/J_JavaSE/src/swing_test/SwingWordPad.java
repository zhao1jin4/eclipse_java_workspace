package swing_test;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;


class World extends JFrame implements ActionListener
{	
	JMenu menu;
	JMenuBar menubar;
	JMenuItem open;
	JMenuItem exit;
	JMenuItem save;
	JMenuItem saveas;
	JTextArea text;
	//JTextPane text;  //JEditorPane
	JFileChooser chooser;
	
	File openfile;
	JScrollPane pane;
	FileReader filereader;
	FileInputStream input;
	Filter myfilter=new Filter();
	
	
	
	public World(String name)
	{
		super(name);
		menubar=new JMenuBar();
		menu=new JMenu("file");
		open=new JMenuItem("打开");
		exit=new JMenuItem("退出");
		save=new JMenuItem("保存");
		saveas=new JMenuItem("保存为..");
		menu.add(open);
		menu.add(save);
		menu.add(saveas);
		menu.add(exit);
		menubar.add(menu);
		open.addActionListener(this);
		exit.addActionListener(this);
		save.addActionListener(this);
		saveas.addActionListener(this);
		
		text=new JTextArea();
		text.setLineWrap(true);       //设置自动换行
		pane=new JScrollPane(text);
		//pane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		chooser=new JFileChooser("d:\\");
		setJMenuBar(menubar);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(pane,BorderLayout.CENTER);
		
	}
	class Filter extends FileFilter
	{
		public boolean accept(File f)  
		{
			if(f.isFile())
			{
				int begin =f.getName().lastIndexOf(".");    //v如文件名有"."即非扩展名,则要用lastIndexOf
				if (begin<=0) return false;                 //为了有没扩展名的文件做判断   
				//int end=f.getName().length();
				if(f.getName().substring(begin).equals(".txt"))  
				{return true;}
				else return false;
			}
			else if(f.isDirectory())return true;
			else return false;
		}
		 public String getDescription() 
		 {
			return "txt doucment*.txt"; 
		 }
	}
	public void saveAsFile()
	{	
		chooser.setDialogTitle("请选择保存的文件的位置");     //没有解决自动增加扩展名
		int state=chooser.showSaveDialog(this);           
		
		openfile=chooser.getSelectedFile();
		chooser.setFileFilter(myfilter);            //再次设置FileFilter同　样的过滤器问题
		if(state==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				FileOutputStream out=new FileOutputStream(openfile);
				byte[] by=text.getText().getBytes();
				
				out.write(by,0,by.length);
				
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
			
	}
	public void actionPerformed(ActionEvent e)
	{
				
	
		if(e.getActionCommand().equals("打开"))      //问题:打开文件有多余的空格
		{	text.setText("");                       //问题1 :打开文件时会自动记录重复的Filter过虑器
			try	{
				chooser.setDialogTitle("请选择打开的文件"); 
				
				chooser.setFileFilter(myfilter);
				int state=chooser.showOpenDialog(this);
				openfile=chooser.getSelectedFile();
					if(state==JFileChooser.APPROVE_OPTION)
					{
						input =new FileInputStream(openfile);
						byte[] buffer=new byte[1024];
								int len;
						while((len=(input.read(buffer)))!=-1 )
							{
								text.append(new String(buffer,0,len));   //是否解决 OK
								
							}
						
					}
				}
			catch (Exception ex )
				{
					ex.printStackTrace();
				}
		}
		else if(e.getActionCommand().equals("退出"))
		{
			World.this.dispose();
		}
		else if (e.getActionCommand().equals("保存为.."))
		{
			saveAsFile();
				
		}else if(e.getActionCommand().equals("保存"))
		{
			
			if(openfile==null)
			{
				saveAsFile();
				
			}else 
			{
				try{
					FileOutputStream out=new FileOutputStream(openfile);
					out.write(text.getText().getBytes());
									
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
			
	}
		
}

public class SwingWordPad 
{
	

	public static void main(String[] args) 
	{
		World myword =new World("我的小记事本");
		myword.setSize(300,300);
		myword.setVisible(true);
		

	}

}
