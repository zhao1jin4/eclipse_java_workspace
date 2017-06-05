package crc.util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CRCSwing extends JFrame implements ActionListener
{
	JLabel fileLable=new JLabel("select File:");
	JTextField  fileText=new JTextField();
	JButton  fileButton=new JButton("browser...");
	JFileChooser fileChoose=new JFileChooser();
	JLabel crcLable=new JLabel("CRC:");
	JTextField crcText=new JTextField();

	JButton button=new JButton("generate");
	
	
	public CRCSwing()
	{
		this.getContentPane().setLayout(new GridLayout(2,3));
		
		this.add(fileLable);
		this.add(fileText);
		this.add(fileButton);
		
		this.add(crcLable);
		this.add(crcText);
 		
		button.addActionListener(this);
		this.add(button);
		
		fileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int result=fileChoose.showOpenDialog(getFrame());
				if(result == JFileChooser.APPROVE_OPTION)
				{ 
					fileText.setText(fileChoose.getSelectedFile().getPath()); 
		 		} 
				
			}});
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public JFrame getFrame()
	{
		return this;
	}
	
	public void actionPerformed(ActionEvent e)
	{
	
		if(fileText.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, "please select a file");
		}else
		{
			FileInputStream input=null;
			try
			{
				input =new FileInputStream(fileText.getText());
				CRC32 crc=new CRC32();
				byte[]buffer=new byte[1024];
				int readed=0;
				while(  (readed=input.read(buffer))!= -1 )
				{
					crc.update(buffer,0,readed);
				}
				
				long crc_res=crc.getValue();
				
				crcText.setText(Long.valueOf((crc_res)).toString());
				
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}finally
			{
				try
				{input.close();} 
				catch (IOException e1){e1.printStackTrace();}
			}
		}
		
	}
	public static void main(String[] args)
	{
		new CRCSwing();
	}
}
