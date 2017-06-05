
package net;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SwingURLDownload extends JFrame implements ActionListener 
{
	JButton down;
	JTextField text;
	JTextArea area;
	JLabel label;
	JPanel panel;
	
	public SwingURLDownload(String title)
	{
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		down=new JButton("下载");
		text =new JTextField(20);
		label=new JLabel("please input your URL");
		panel=new JPanel();
		panel.add(label);
		panel.add(text);
		area=new JTextArea();
		JScrollPane scroll=new JScrollPane(area);
		//scroll.add(area);
		area.setEditable(false);
		getContentPane().add(panel,"North");
		getContentPane().add(scroll,"Center");
		getContentPane().add(down,"South");
		down.addActionListener(this);
		
		
	}
	public void actionPerformed(ActionEvent e)
	{
		String text=this.text.getText();
		try
		{
			URL url=new URL(text);
			URLConnection urlcon=url.openConnection();
			InputStream instream=urlcon.getInputStream();
			//InputStreamReader read=new InputStreamReader(instream);
			//BufferedReader bufread=new BufferedReader(read);
			byte[] readBuffer=new byte[1000];
			String str;
			FileOutputStream fileout=new FileOutputStream("c:/url_out.html");
			int i;
			//while((str=bufread.readLine())!=null)
			BufferedOutputStream outPut=new BufferedOutputStream(fileout);
			while((i=instream.read(readBuffer))!=-1)
			{			
				outPut.write(readBuffer);
				//fileout.write((System.getProperty("line.separator").getBytes()));
			}
			area.append(System.getProperty("line.separator"));
			area.append(url.getHost());
			area.append(System.getProperty("line.separator"));
			area.append(new Integer(urlcon.getContentLength()).toString());
			area.append(System.getProperty("line.separator"));
			area.append(new Integer(url.getDefaultPort()).toString());
			area.append(System.getProperty("line.separator"));
			area.append(urlcon.getContentType());
			area.append(System.getProperty("line.separator"));
			//bufread.close();
			instream.close();
			
			outPut.close();
//			fileout.close();
			
			
		}catch (MalformedURLException ex)
		{
			ex.printStackTrace();
		}catch(Exception exe)
		{
			exe.printStackTrace();
			
		}
	}

	public static void main(String[] args) 
	{
		SwingURLDownload window=new SwingURLDownload("我的下载小程序");
		window.setSize(600,600);
	
		Toolkit tool= Toolkit.getDefaultToolkit();
		Dimension d=tool.getScreenSize();
		window.setLocation((int)d.getWidth()/2-600/2,(int)d.getHeight()/2-600/2);
		
		window.setVisible(true);	
		
	}

}//http://www.baidu.com/img/logo.gif
