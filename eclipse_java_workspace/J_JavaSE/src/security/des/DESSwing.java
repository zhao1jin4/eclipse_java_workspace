package security.des;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


public class DESSwing extends JFrame {
	JButton encButton=new JButton("Encode Password");
	JTextField encKeyField=new JTextField(8);
	JTextField encPassField=new JTextField(20);
	JTextField encResultField=new JTextField(20);
	//--
	JButton decButton=new JButton("Decode Password");
	JTextField decKeyField=new JTextField(8);
	JTextField decPassField=new JTextField(20);
	JTextField decResultField=new JTextField(20);
	public DESSwing()
	{
		
		//---encode
		final	JPanel encodePanel=new  JPanel();
		
		encodePanel.setLayout(new BoxLayout(encodePanel,BoxLayout.PAGE_AXIS));
		
		JPanel keyPanel=new JPanel();
		keyPanel.add(new JLabel("    key:  "));
		keyPanel.add(encKeyField);
		encodePanel.add(keyPanel);
		
		JPanel passEncodePanel=new JPanel();
		passEncodePanel.add(new JLabel("   password : "));
		passEncodePanel.add(encPassField);
		encodePanel.add(passEncodePanel);
		
		JPanel encPassPanel=new JPanel();
		encPassPanel.add(new JLabel("encoded password:"));
		encPassPanel.add(encResultField);
		encodePanel.add(encPassPanel);
		
		encodePanel.add(encButton);
		encButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				String key=encKeyField.getText();
				if(key.length()!=8)
				{
					JOptionPane.showMessageDialog(encodePanel,"the key must be length 8","error",JOptionPane.ERROR_MESSAGE);
					encResultField.setText("");
					return;
				}
				if(encPassField.getText().length()==0)
				{
					JOptionPane.showMessageDialog(encodePanel,"password is empty","error",JOptionPane.ERROR_MESSAGE);
					encResultField.setText("");
					return;
				}
				String pass=encPassField.getText();
				String encode=DESUtil.encryptString(pass, key);
				encResultField.setText(encode);
			}
		});
		
		
		JTabbedPane tab=new JTabbedPane();
		tab.add(encodePanel);
		tab.setTitleAt(0, "Encode DES");
		
		
		//---------decode
		final	JPanel decodePanel=new  JPanel();
		decodePanel.setLayout(new BoxLayout(decodePanel,BoxLayout.PAGE_AXIS));
		JPanel keyDecodePanel=new JPanel();
		keyDecodePanel.add(new JLabel("    key:  "));
		keyDecodePanel.add(decKeyField);
		decodePanel.add(keyDecodePanel);
		
		JPanel passDecodePanel=new JPanel();
		passDecodePanel.add(new JLabel("   password : "));
		passDecodePanel.add(decPassField);
		decodePanel.add(passDecodePanel);
		
		JPanel decPassPanel=new JPanel();
		decPassPanel.add(new JLabel("decoded password:"));
		decPassPanel.add(decResultField);
		decodePanel.add(decPassPanel);
		
		decodePanel.add(decButton);
		decButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				String key=decKeyField.getText();
				if(key.length()!=8)
				{
					JOptionPane.showMessageDialog(decodePanel,"the key must be length 8","error",JOptionPane.ERROR_MESSAGE);
					decResultField.setText("");
					return;
				}
				int len=decPassField.getText().length();
				if(decPassField.getText().length()==0 || len%8!=0 )
				{
					JOptionPane.showMessageDialog(encodePanel,"password can not empty or must be multiple of 8 ","error",JOptionPane.ERROR_MESSAGE);
					decResultField.setText("");
					return;
				}
				String pass=decPassField.getText();
				String decode=DESUtil.decryptString(pass, key);
				decResultField.setText(decode);
			}
		});
		tab.add(decodePanel);
		tab.setTitleAt(1, "Decode DES");

		
		
		this.add(tab);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		 DESSwing t= new DESSwing();
		 t.setVisible(true);
		 t.pack();
		
	}
	

}
