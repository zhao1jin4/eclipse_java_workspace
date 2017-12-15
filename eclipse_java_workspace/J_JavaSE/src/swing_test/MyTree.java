package swing_test;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.JFrame;
import javax.swing.tree.*;



public class MyTree extends JFrame 
{
	DefaultMutableTreeNode computer=new DefaultMutableTreeNode("coputer");
	DefaultMutableTreeNode c=new DefaultMutableTreeNode("c");
	DefaultMutableTreeNode d=new DefaultMutableTreeNode("d");
	DefaultMutableTreeNode mydocument=new DefaultMutableTreeNode("mydocumnet");
	DefaultMutableTreeNode windows=new DefaultMutableTreeNode("windows");
	DefaultMutableTreeNode java=new DefaultMutableTreeNode("java");
	DefaultMutableTreeNode oracle=new DefaultMutableTreeNode("oracle");
	
	public MyTree(String title)
	{	super(title);
		computer.add(c);
		computer.add(d);
		c.add(mydocument);
		c.add(windows);
		d.add(java);
		d.add(oracle);
		//DefaultTreeModel treenode=new DefaultTreeModel(computer);
		
		
		JTree tree=new JTree(computer);//treenode
		this.add(tree);
		setSize(100,500);
		setVisible(true);
		
	}
	
	
public static void main(String args[])
{
	MyTree mytree=new MyTree("this is my JTree");
	
}
}
