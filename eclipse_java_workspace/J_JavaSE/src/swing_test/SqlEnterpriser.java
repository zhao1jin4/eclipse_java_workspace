package swing_test;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;



class Confirm extends JFrame implements ActionListener
{
	JTextField username;
	JPasswordField password;
	JTextField database;
	JButton ok;
	
	public Confirm(String title)
	{
		super(title);
		username=new JTextField (15);
		password=new JPasswordField(15);
		password.setEchoChar('#');
		database=new JTextField("mydatabase",15);
		ok=new JButton("����");
		ok.addActionListener(this);
				
		setLayout(new GridLayout(4,1));
		
		JLabel l_user=new JLabel("�û���");
		JLabel l_password=new JLabel("����");
		JPanel user=new JPanel();
		user.add(l_user);
		user.add(username);
		
		JPanel pass=new JPanel();
		pass.add(l_password);
		pass.add(password);
		JPanel data=new JPanel();
		JLabel l_data=new JLabel("���ݿ�����");
	
		data.add(l_data);
		data.add(database);
		
		getContentPane();
		add(user);
		add(pass);
		add(data);
		add(ok);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		try{				
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin://@localhost:1521:xe";
			//Class.forName("net.sourceforge.jtds.jdbc.Driver");
			//String url = "jdbc:jtds:sqlServer://localhost:1433:"+database;
			Connection conn = DriverManager.getConnection(url,username.getText(),new String(password.getPassword()));

			if(!conn.isClosed())
			{
				System.out.println("connet success");// 
				
				this.dispose();		
				SqlEnterpriser window=new SqlEnterpriser("SQL Server��ҵ��������ģ��");
				window.setVisible(true);
				window.setSize(500,500);
				conn.close();//�ر�����
			}
		
		
		}catch(Exception ex)
		{
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(this,"����û������������,����������","wrong",JOptionPane.WARNING_MESSAGE);
			System.out.println("������Ĵ���");
		}
		
		
	}
}

/*class Tree extends JTree
{
	DefaultTreeModel model;
	DefaultMutableTreeNode database=new DefaultMutableTreeNode("���ݿ�");
	public Tree()
	{
		model=new DefaultTreeModel(database);
	//add(model);
		
		
	}
	
}
*/


public class SqlEnterpriser extends JFrame

{
	JTree mytree;
	//MyList mylist;
	
	DefaultTreeModel model;
	DefaultMutableTreeNode database;
	
	public SqlEnterpriser(String title)
	{		
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		database=new DefaultMutableTreeNode("���ݿ�",true);
		DefaultMutableTreeNode db_cont=new DefaultMutableTreeNode("empolyee",false);
		
		database.add(db_cont);
		model=new DefaultTreeModel(database);
		mytree=new JTree(model);
		
		
		//mytree=new Tree();
		
		
		
	//	mylist =new MyList();
		add(mytree,BorderLayout.WEST);
		//add(mylist,BorderLayout.EAST);
		
		
	}

	
	public static void main(String[] args) 
	{
		Confirm welcom=new Confirm(" �û���¼");
	
		welcom.setSize(300,300);
		welcom.setVisible(true);
	
		
	}

}
