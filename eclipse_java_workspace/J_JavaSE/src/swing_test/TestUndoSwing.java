package swing_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;


class TextPanel extends JPanel  implements ActionListener
{
	UndoManager textUndoManager=new UndoManager() ;
	JTextArea text=new JTextArea();
	JButton textUndoBtn=new JButton("undoText");
	JButton textRedoBtn=new JButton("redoText");
	public TextPanel()
	{
		textUndoBtn.addActionListener(this);
		textRedoBtn.addActionListener(this);

		this.setLayout(new BorderLayout());
		this.add(text,BorderLayout.CENTER);
		
		JPanel temp=new JPanel();
		temp.add(textUndoBtn);
		temp.add(textRedoBtn);
		this.add(temp,BorderLayout.SOUTH);
		
		
		//-----------JDK demo/jfc/Notepad����ʾ��
		text.getDocument().addUndoableEditListener(new UndoableEditListener()//��ʽ��  addUndoableEditListener
							{
							public void undoableEditHappened(UndoableEditEvent e) {
								TextPanel.this.textUndoManager.addEdit(e.getEdit());//UndoableEdit
							}});
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==textUndoBtn)
		{
			if(textUndoManager.canUndo())//�������Ƿ�ť�����
				textUndoManager.undo();//���˰�ť��
			
		}else if (e.getSource()==textRedoBtn)
		{
			if(textUndoManager.canRedo())
				textUndoManager.redo();
		}
		//textUndoManager.discardAllEdits();//���½��ĵ�ʱ,undo��Ϣ��Ҫ��
	}
	
	
}
class ShapePanel extends JPanel implements ActionListener
{
	
	UndoManager shapeUndoManager=new UndoManager() ;
	JPanel panel=new JPanel();
	JButton shapeUndoBtn=new JButton("undoShape");
	JButton shapeRedoBtn=new JButton("redoShape");
	
	Vector<Shape> shapes=new Vector<Shape> ();
	
	public ShapePanel()
	{
		this.setLayout(new BorderLayout());
		panel.setBackground(Color.YELLOW);
		this.add(panel,BorderLayout.CENTER);
		JPanel temp=new JPanel();
		temp.add(shapeUndoBtn);
		temp.add(shapeRedoBtn);
		this.add(temp,BorderLayout.SOUTH);
		
		shapeUndoBtn.addActionListener(this);
		shapeRedoBtn.addActionListener(this);
		panel.addMouseListener(new MouseAdapter()
		{
			int x;
			int y;
			public void mousePressed(MouseEvent e) {
				x=e.getX();
				y=e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				Graphics g=panel.getGraphics();
				int w;
				int h;
				w=e.getX()-x;
				h=e.getY()-y;
				
				if(e.getX()-x <0)
				{
					w=x-e.getX();
					x=e.getX();
					
				}
				if(e.getY()-y <0)
				{
					h=y-e.getY();
					y=e.getY();
				}
				
				
				/*
				UndoableEdit inter;//�ӿ�
				AbstractUndoableEdit aaa;//ʵ����UndoableEdit
				StateEditable in=null;//�ӿ�
				StateEdit state=new StateEdit(in);//�̳���AbstractUndoableEdit 
				//�粻���ı�,���ǻ�ͼ��,Ҫ�Լ����� addEdit(�Լ�ʵ��AbstractUndoableEdit�е�undo,redo)
				*/
				
				Shape rec=new Rectangle(x,y,w,h);
				shapes.add(rec);
				((Graphics2D)g).draw(rec);
				shapeUndoManager.addEdit(new GraphEdit(rec)); //��ʽһ addEdit()
				
			}
			
		});
		
	}
	public void paint(Graphics g) 
	{
		super.paint(g);
		Graphics2D g2=((Graphics2D)g);
		for(Shape s :shapes)
		{
			g2.draw(s);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==shapeUndoBtn)
		{
			if(shapeUndoManager.canUndo())
				shapeUndoManager.undo();
		}else if (e.getSource()==shapeRedoBtn)
		{
			if(shapeUndoManager.canRedo())
				shapeUndoManager.redo();
		}		
	}
	
	 class GraphEdit extends AbstractUndoableEdit
	 { 
		 Shape shape; 
		 public GraphEdit(Shape _shape)
		 { 
		     shape = _shape; 
		 } 
		 public void undo()
		 {
			 super.undo();
		     shapes.remove(shape); //�� ShapePanel��shapes
		     repaint(); //�� ShapePanel���� �ػ�����paint����
		 } 
		 public void redo(){ 
			 super.redo();
		     shapes.add(shape);//�� ShapePanel��shapes
		     repaint(); //�� ShapePanel���� �ػ�����paint����
		 } 
	}
	
}
public class TestUndoSwing  extends JFrame 
{
	JTabbedPane tab=new JTabbedPane();
	
	
	public TestUndoSwing()
	{
		tab.add(new TextPanel());
		tab.setTitleAt(0, "Text Undo");
		
		tab.add(new ShapePanel());
		tab.setTitleAt(1, "Shape undo");
		
		this.getContentPane().add(tab);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) 
	{
		new TestUndoSwing();
	}

}
