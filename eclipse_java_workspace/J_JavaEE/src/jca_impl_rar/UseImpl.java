package jca_impl_rar;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.RecordFactory;

import jca_impl_rar.HelloWorldIndexedRecord;

public class UseImpl {
	String  message;
	public void execute() throws NamingException, ResourceException {

		InitialContext context = new InitialContext();
		ConnectionFactory cxFactory = (ConnectionFactory) context.lookup("java:comp/env/HelloWorld");
		RecordFactory recordFactory = cxFactory.getRecordFactory();
		IndexedRecord input = recordFactory.createIndexedRecord(HelloWorldIndexedRecord.INPUT);
		IndexedRecord output = recordFactory.createIndexedRecord(HelloWorldIndexedRecord.OUTPUT);
		InteractionSpec ispec = (InteractionSpec) context.lookup("jca/HelloWorldISpec");
		
		Connection connection = cxFactory.getConnection();
		Interaction interaction = connection.createInteraction();
		interaction.execute(ispec, input, output);
		message = (String) output.get(HelloWorldIndexedRecord.MESSAGE_FIELD);
		interaction.close();
		connection.close();
	}

	public static void main(String[] args) {

	}

}
