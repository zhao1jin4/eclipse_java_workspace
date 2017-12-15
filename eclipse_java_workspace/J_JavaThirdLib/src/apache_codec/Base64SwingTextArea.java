package apache_codec;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


public class Base64SwingTextArea extends JFrame implements ActionListener
{
	JTabbedPane tab=new JTabbedPane();
	JPanel charsetPanel=new JPanel();
	JComboBox<String> charsetComboBox=new JComboBox<String>();
	Charset charset=Charset.defaultCharset();//系统默认
	
	
	JLabel inEncLable=new JLabel("the text fo encode:");
	JTextArea inEncText=new JTextArea();
	JScrollPane  inEncScroll=new JScrollPane(inEncText);
	
	JLabel outEncLable=new JLabel("the encode result text:");
	JTextArea outEncText=new JTextArea();
	JScrollPane  outEncScroll=new JScrollPane(outEncText);
	
	
	JButton  encButton=new JButton("encode");
	JPanel encPanel=new JPanel();
	
	//decode
	
	JLabel inDecLable=new JLabel("the text for decode:");
	JTextArea inDecText=new JTextArea();
	JScrollPane  inDecScroll=new JScrollPane(inDecText);
	
	JLabel outDecLable=new JLabel("the decode result text:");
	JTextArea outDecText=new JTextArea();
	JScrollPane outDecScroll=new JScrollPane(outDecText);
	
	JButton  decButton=new JButton("decode");
	JPanel decPanel=new JPanel();
	
	public Base64SwingTextArea()
	{
		
		encPanel.setLayout(new GridLayout(3,2));
		encPanel.add(inEncLable);
		inEncScroll.setPreferredSize(new Dimension(300,100));
		encPanel.add(inEncScroll);
		//-----
		encPanel.add(outEncLable);
		encPanel.add(outEncScroll);
		//-----
		encPanel.add(encButton);
		
		//decode
		decPanel.setLayout(new GridLayout(3,2));
		decPanel.add(inDecLable);
		inEncScroll.setPreferredSize(new Dimension(300,100));
		decPanel.add(inDecScroll);
		//-----
		decPanel.add(outDecLable);
		decPanel.add(outDecScroll);
		//-----
		decPanel.add(decButton);
		
		encButton.addActionListener(this);
		decButton.addActionListener(this);
		
		tab.add(encPanel);
		tab.setTitleAt(0, "Base64 Encode");
		
		tab.add(decPanel);
		tab.setTitleAt(1, "Base64 Decode");
		
		
		//-----
		
		charsetPanel.add(new JLabel("select base64 encode/decode use charset:"));
		charsetPanel.add(charsetComboBox);
		
		charsetComboBox.addItem("UTF-8");//默认UTF-8
		charsetComboBox.addItem("GBK");
		
//		String defaultCharset=System.getProperties().get("file.encoding").toString();
//		if("GBK".equals(defaultCharset) ) // file.encoding=GBK
//		{
//			charsetComboBox.setSelectedIndex(1);
//		}
		
		if(charset.displayName().equals("GBK"))
		{
			charsetComboBox.setSelectedIndex(1);
		}
		
		
		charsetComboBox.addItemListener(new MyCombItemListener() );
		
		//-----
		this.setLayout(new BorderLayout());
		this.add(charsetPanel,BorderLayout.NORTH);
		this.add(tab,BorderLayout.CENTER);
		
		this.setVisible(true);
		this.pack();
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class MyCombItemListener  implements  ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e) 
		{ 
			if(e.getStateChange()==ItemEvent.SELECTED)// 有一次原来的项未选择事件,再是新项的选择事件
			{
				String selected=(String) (e.getItem());
				if( selected.equals("UTF-8"))
					charset=Charset.forName("UTF-8");
				else
					charset=Charset.forName("GBK");
				}	
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==encButton)
		{ 
			String inStr=inEncText.getText();
			byte[]buffer=inStr.getBytes(charset); 
			
			byte[]resultData=ApacheBase64Util.base64Encode(buffer);//以后加进度条,使用byte[]的方法,在Apache也有codec实现
			//byte[]resultData=SunBase64Util.base64Encode(buffer);//为何和InputStream参数的结果不同??
			//byte[]resultData=JDKBase64Util.base64Encode(buffer);
			
			outEncText.setText(new String(resultData,charset));
			
			JOptionPane.showMessageDialog(this, "Base64 encode finished");
		}else if (e.getSource()==decButton)
		{
			String inStr=inDecText.getText();
			byte[]buffer=inStr.getBytes(charset); 
			byte[]resultData=ApacheBase64Util.base64Decode(buffer);//以后加进度条,使用byte[]的方法,在Apache也有codec实现
			//byte[]resultData=SunBase64Util.base64Decode(buffer);//为何和InputStream参数的结果不同??
			
			outDecText.setText(new String(resultData,charset));
			
			JOptionPane.showMessageDialog(this, "Base64 decode finished");
		
		}
	}
	 
	public static void main(String[] args) throws Exception
	{
		new Base64SwingTextArea();
	}
}
