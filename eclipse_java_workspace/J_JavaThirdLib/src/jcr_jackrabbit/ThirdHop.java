package jcr_jackrabbit;

import java.io.File;
import java.io.InputStream;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;

import org.apache.jackrabbit.core.TransientRepository;

public class ThirdHop
{
	public static void main(String[] args) throws Exception
	{
		Repository repository = new TransientRepository(new File("d:/temp/jackrabit_repos"));
		Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		try
		{
			Node root = session.getRootNode();
			if (!root.hasNode("importxml"))
			{
				System.out.print("Importing xml... ");
				Node node = root.addNode("importxml", "nt:unstructured");
				InputStream is = ThirdHop.class.getResourceAsStream("/jcr_jackrabbit/data.xml");
				session.importXML(node.getPath(), is, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
				is.close();
				session.save();
				System.out.println("done.");
			}
			dump(root);
		} finally
		{
			session.logout();
		}
	}

	private static void dump(Node node) throws RepositoryException
	{
		System.out.println(node.getPath());
		if (node.getName().equals("jcr:system"))
			return;
		PropertyIterator properties = node.getProperties();
		while (properties.hasNext())
		{
			Property property = properties.nextProperty();
			if (property.getDefinition().isMultiple())
			{
				Value[] values = property.getValues();
				for (int i = 0; i < values.length; i++)
				{
					System.out.println(property.getPath() + " = " + values[i].getString());
				}
			} else
				System.out.println(property.getPath() + " = " + property.getString());
		}
		NodeIterator nodes = node.getNodes();
		while (nodes.hasNext())
			dump(nodes.nextNode());
	}

}