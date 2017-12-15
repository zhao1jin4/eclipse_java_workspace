package jca_impl_rar;

/***************************************************************************/
/*                                                                         */
/* (c) Copyright IBM Corp. 2002  All rights reserved.                      */
/*                                                                         */
/* This sample program is owned by International Business Machines         */
/* Corporation or one of its subsidiaries ("IBM") and is copyrighted       */
/* and licensed, not sold.                                                 */
/*                                                                         */
/* You may copy, modify, and distribute this sample program in any         */
/* form without payment to IBM, for any purpose including developing,      */
/* using, marketing or distributing programs that include or are           */
/* derivative works of the sample program.                                 */
/*                                                                         */
/* The sample program is provided to you on an "AS IS" basis, without      */
/* warranty of any kind.  IBM HEREBY  EXPRESSLY DISCLAIMS ALL WARRANTIES,  */
/* EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED   */
/* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.     */
/* Some jurisdictions do not allow for the exclusion or limitation of      */
/* implied warranties, so the above limitations or exclusions may not      */
/* apply to you.  IBM shall not be liable for any damages you suffer as    */
/* a result of using, modifying or distributing the sample program or      */
/* its derivatives.                                                        */
/*                                                                         */
/* Each copy of any portion of this sample program or any derivative       */
/* work,  must include the above copyright notice and disclaimer of        */
/* warranty.                                                               */
/*                                                                         */
/***************************************************************************/

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

public class HelloWorldConnectionFactoryImpl implements ConnectionFactory {

	private Reference reference;
	private ConnectionManager cm;
	private ManagedConnectionFactory mcf;

	/**
	 * Constructor for HelloWorldConnectionFactoryImpl
	 */
	public HelloWorldConnectionFactoryImpl(
		ManagedConnectionFactory mcf,
		ConnectionManager cm) {

		super();
		this.mcf = mcf;
		this.cm = cm;
	}

	/**
	 * @see ConnectionFactory#getConnection()
	 */
	public Connection getConnection() throws ResourceException {

		return (Connection) cm.allocateConnection(mcf, null);
	}

	/**
	 * @see ConnectionFactory#getConnection(ConnectionSpec)
	 */
	public Connection getConnection(ConnectionSpec connectionSpec) throws ResourceException {

		return getConnection();
	}

	/**
	 * @see ConnectionFactory#getRecordFactory()
	 */
	public RecordFactory getRecordFactory() throws ResourceException {

		return new HelloWorldRecordFactoryImpl();
	}

	/**
	 * @see ConnectionFactory#getMetaData()
	 */
	public ResourceAdapterMetaData getMetaData() throws ResourceException {

		return new HelloWorldResourceAdapterMetaDataImpl();
	}

	/**
	 * @see Referenceable#setReference(Reference)
	 */
	public void setReference(Reference reference) {

		this.reference = reference;
	}

	/**
	 * @see Referenceable#getReference()
	 */
	public Reference getReference() throws NamingException {

		return reference;
	}

}