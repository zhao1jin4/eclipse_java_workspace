package org.zhaojin.adapter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class AdapterTest {

	public static void main(String[] args) 
	{
		 //JDK 的示例
		JButton b=new JButton();
		b.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent arg0) {
			}
				
		});
		b.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
			}
			 
		});
	}

}
