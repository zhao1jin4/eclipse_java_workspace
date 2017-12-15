package apache_codec;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


public class Base64SwingFile extends JFrame implements ActionListener
{
	JTabbedPane tab=new JTabbedPane();
	
	JLabel inEncFileLable=new JLabel("choose file to encode:");
	JTextField  inEncfileText=new JTextField();
	JButton  inEncBroser=new JButton("browser...");
	JFileChooser inEncfileChoose=new JFileChooser();
	
	JLabel outEncFileLable=new JLabel("save result to file:");
	JTextField  outEncfileText=new JTextField();
	JButton  outEncBroser=new JButton("browser...");
	JFileChooser outEncfileChoose=new JFileChooser();
	
	JButton  encButton=new JButton("encode");
	Panel encPanel=new Panel();
	//decode
	
	JLabel inDecFileLable=new JLabel("choose file to decode:");
	JTextField  inDecfileText=new JTextField();
	JButton  inDecBroser=new JButton("browser...");
	JFileChooser inDecfileChoose=new JFileChooser();
	
	JLabel outDecFileLable=new JLabel("save result to file:");
	JTextField  outDecfileText=new JTextField();
	JButton  outDecBroser=new JButton("browser...");
	JFileChooser outDecfileChoose=new JFileChooser();
	
	JButton  decButton=new JButton("decode");
	Panel decPanel=new Panel();
	
	public Base64SwingFile()
	{
		
		encPanel.setLayout(new GridLayout(3,3));
		encPanel.add(inEncFileLable);
		encPanel.add(inEncfileText);
		encPanel.add(inEncBroser);
		//-----
		encPanel.add(outEncFileLable);
		encPanel.add(outEncfileText);
		encPanel.add(outEncBroser);
		//-----
		encPanel.add(encButton);
		
		//decode
		decPanel.setLayout(new GridLayout(3,3));
		decPanel.add(inDecFileLable);
		decPanel.add(inDecfileText);
		decPanel.add(inDecBroser);
		//-----
		decPanel.add(outDecFileLable);
		decPanel.add(outDecfileText);
		decPanel.add(outDecBroser);
		//-----
		decPanel.add(decButton);
		
		
		
		inEncBroser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result=inEncfileChoose.showOpenDialog(encPanel);//open
				if(result == JFileChooser.APPROVE_OPTION)
				{ 
					inEncfileText.setText(inEncfileChoose.getSelectedFile().getPath()); 
		 		} 
			}
		});
		
		outEncBroser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result=outEncfileChoose.showSaveDialog(encPanel);//save
				if(result == JFileChooser.APPROVE_OPTION)
				{ 
					outEncfileText.setText(outEncfileChoose.getSelectedFile().getPath()); 
		 		} 
			}
		});
		
		//decode
		inDecBroser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result=inDecfileChoose.showOpenDialog(decPanel);//open
				if(result == JFileChooser.APPROVE_OPTION)
				{ 
					inDecfileText.setText(inDecfileChoose.getSelectedFile().getPath()); 
		 		} 
			}
		});
		
		outDecBroser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result=outDecfileChoose.showSaveDialog(decPanel);//save
				if(result == JFileChooser.APPROVE_OPTION)
				{ 
					outDecfileText.setText(outDecfileChoose.getSelectedFile().getPath()); 
		 		} 
			}
		});
		
		encButton.addActionListener(this);
		decButton.addActionListener(this);
		
		tab.add(encPanel);
		tab.setTitleAt(0, "Base64 Encode");
		
		tab.add(decPanel);
		tab.setTitleAt(1, "Base64 Decode");

		this.add(tab);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==encButton)
		{
			if(inEncfileText.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "please choose a file  for encode");
				return;
			}
			if(outEncfileText.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "please choose path to save file");
				return;
			}
			if(inEncfileText.getText().equals(outEncfileText.getText()))
			{
				JOptionPane.showMessageDialog(this, "two file can not  same path");
				return;
			}
			
			FileInputStream input=null;
			FileOutputStream output=null;
			try
			{
				File file=new File(inEncfileText.getText());
				long fileSize=file.length();
				input=new FileInputStream(file);
				File outFile=new File(outEncfileText.getText());
				if(outFile.exists())
				{
					int result=JOptionPane.showConfirmDialog(this, "save file already exist,are you sure override?");
					if(result == JOptionPane.NO_OPTION  || result == JOptionPane.CANCEL_OPTION) 
						return;
				}
				output=new FileOutputStream(outFile);//create file
				
				//-----------Stream方式和byte[]方式不同?????
//				SunBase64Util.base64Encode(input,output);
				
				float readCount=0;
				byte[]buffer=new byte[1024];//
				int len=0;
				while((len=input.read(buffer))!=-1)
				{
					readCount+=len;
					byte[]resultData=ApacheBase64Util.base64Encode(buffer);//以后加进度条,使用byte[]的方法,在Apache也有codec实现
					//byte[]resultData=SunBase64Util.base64Encode(buffer);//为何和InputStream参数的结果不同??
					//byte[]resultData=JDKBase64Util.base64Encode(buffer);
					output.write(resultData,0,len);
					System.out.println("complete:"+ readCount/fileSize+"%");
				}
				/*    */
				
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}finally
			{
				try{
					input.close();
					if(output!=null)
						output.close();
				}catch(Exception ex1){ex1.printStackTrace();}
			}
			JOptionPane.showMessageDialog(this, "Base64 encode finished");
		}else if (e.getSource()==decButton)
		{
			if(inDecfileText.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "please choose a file  for decode");
				return;
			}
			if(outDecfileText.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "please choose path to save file");
				return;
			}
			if(inDecfileText.getText().equals(outDecfileText.getText()))
			{
				JOptionPane.showMessageDialog(this, "two file can not  same path");
				return;
			}
			
			FileInputStream input=null;
			FileOutputStream output=null;
			try
			{
				File file=new File(inDecfileText.getText());
				long fileSize=file.length();
				input=new FileInputStream(file);
				File outFile=new File(outDecfileText.getText());
				if(outFile.exists())
				{
					int result=JOptionPane.showConfirmDialog(this, "save file already exist,are you sure override?");
					if(result == JOptionPane.NO_OPTION  || result == JOptionPane.CANCEL_OPTION) 
						return;
				}
				output=new FileOutputStream(outFile);//create file
				
				//-----------Stream方式和byte[]方式不同?????
				//SunBase64Util.base64Decode(input,output);
				
				
				
				float readCount=0;
				byte[]buffer=new byte[(int)fileSize];//应该多大呢,与SUN使用Steam的结果不同?????????
				int len=0;
				while((len=input.read(buffer))!=-1)
				{
					readCount+=len;
					byte[]resultData=ApacheBase64Util.base64Decode(buffer);//以后加进度条,使用byte[]的方法,在Apache也有codec实现
					//byte[]resultData=SunBase64Util.base64Decode(buffer);//为何和InputStream参数的结果不同??
					output.write(resultData,0,len);
					System.out.println("complete:"+ (readCount/fileSize*100)+"%");
				}
			
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}finally
			{
				try{
					input.close();
					if(output!=null)
						output.close();
				}catch(Exception ex1){ex1.printStackTrace();}
			}
			
			JOptionPane.showMessageDialog(this, "Base64 decode finished");
		
		}
	}
	 
	public static void main(String[] args) throws Exception
	{
		new Base64SwingFile();
	}
}
