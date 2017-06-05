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

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

public class HelloWorldManagedConnectionFactoryImpl
	implements ManagedConnectionFactory {

	private PrintWriter writer;

	/**
	 * Constructor for HelloWorldManagedConnectionFactoryImpl
	 */
	public HelloWorldManagedConnectionFactoryImpl() {

		super();
	}

	/**
	 * @see ManagedConnectionFactory#createConnectionFactory(ConnectionManager)
	 */
	public Object createConnectionFactory(ConnectionManager cm)
		throws ResourceException {

		return new HelloWorldConnectionFactoryImpl(this, cm);
	}

	/**
	 * @see ManagedConnectionFactory#createConnectionFactory()
	 */
	public Object createConnectionFactory() throws ResourceException {

		return new HelloWorldConnectionFactoryImpl(this, null);
	}

	/**
	 * @see ManagedConnectionFactory#createManagedConnection(Subject, ConnectionRequestInfo)
	 */
	public ManagedConnection createManagedConnection(
		Subject subject,
		ConnectionRequestInfo cxRequestInfo)
		throws ResourceException {

		return new HelloWorldManagedConnectionImpl();
	}

	/**
	 * @see ManagedConnectionFactory#matchManagedConnections(Set, Subject, ConnectionRequestInfo)
	 */
	public ManagedConnection matchManagedConnections(
		Set connectionSet,
		Subject subject,
		ConnectionRequestInfo cxRequestInfo)
		throws ResourceException {

		ManagedConnection match = null;
		Iterator iterator = connectionSet.iterator();
		if (iterator.hasNext()) {
			match = (ManagedConnection) iterator.next();
		}

		return match;
	}

	/**
	 * @see ManagedConnectionFactory#setLogWriter(PrintWriter)
	 */
	public void setLogWriter(PrintWriter writer) throws ResourceException {

		this.writer = writer;
	}

	/**
	 * @see ManagedConnectionFactory#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws ResourceException {

		return writer;
	}

	public boolean equals(Object other) {

		if (other instanceof HelloWorldManagedConnectionFactoryImpl) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		
		return 0;
	}

}