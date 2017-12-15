package swing_test;
import javax.swing.*;
import java.awt.*;

public class  JRadioButtonTest
{
	public static void main(String[] args) 
	{
		
		JRadioButton radio1 =new JRadioButton("man");
		JRadioButton radio2 =new JRadioButton("woman");
		JRadioButton radio3 =new JRadioButton("animal");
		ButtonGroup group=new ButtonGroup();// 获取选择的值

		JFrame frame=new JFrame("myframe");
		frame.setLayout(new FlowLayout());
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		frame.add(radio1);
		frame.add(radio2);
		frame.add(radio3);


		JMenu menu=new JMenu("file");
JMenuBar bar=new JMenuBar();

//JCheckBoxMenuItem, JMenu, JRadioButtonMenuItem 
JMenuItem item=new JMenuItem("first");
JCheckBoxMenuItem check1=new JCheckBoxMenuItem("football");
JCheckBoxMenuItem check2=new JCheckBoxMenuItem("basketball");
JCheckBoxMenuItem check3=new JCheckBoxMenuItem("pingpong");


JRadioButtonMenuItem aa=new JRadioButtonMenuItem("aa");
JRadioButtonMenuItem bb=new JRadioButtonMenuItem("bb");
JRadioButtonMenuItem cc=new JRadioButtonMenuItem("cc");

ButtonGroup  gg=new ButtonGroup();
gg.add(aa);
gg.add(bb);
gg.add(cc);


menu.add(aa);
menu.add(bb);
menu.add(cc);


bar.add(menu);
menu.add(item);
menu.add(check1);
menu.add(check2);
menu.add(check3);
frame.setJMenuBar(bar);




		frame.setSize(400,500);
		frame.setVisible(true);



	}
}
