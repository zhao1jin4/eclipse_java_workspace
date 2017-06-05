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
import java.util.Enumeration;
import java.util.Vector;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

public class HelloWorldManagedConnectionImpl implements ManagedConnection {

	private static final String TRANSACTIONS_NOT_SUPPORTED_ERROR =
		"Transactions not supported";

	private HelloWorldConnectionImpl connection;
	private Vector listeners = new Vector();
	private PrintWriter out;

	/**
	 * Constructor for HelloWorldManagedConnectionImpl
	 */
	public HelloWorldManagedConnectionImpl() {

		super();
	}

	public void close() {

		Enumeration list = listeners.elements();
		ConnectionEvent event =
			new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
		event.setConnectionHandle(connection);
		while (list.hasMoreElements()) {
			((ConnectionEventListener) list.nextElement()).connectionClosed(event);
		}
	}

	/**
	 * @see ManagedConnection#getConnection(Subject, ConnectionRequestInfo)
	 */
	public Object getConnection(
		Subject subject,
		ConnectionRequestInfo cxRequestInfo)
		throws ResourceException {

		connection = new HelloWorldConnectionImpl(this);
		return connection;
	}

	/**
	 * @see ManagedConnection#destroy()
	 */
	public void destroy() throws ResourceException {

		connection.invalidate();
		connection = null;
		listeners = null;
	}

	/**
	 * @see ManagedConnection#cleanup()
	 */
	public void cleanup() throws ResourceException {

		connection.invalidate();
	}

	/**
	 * @see ManagedConnection#associateConnection(Object)
	 */
	public void associateConnection(Object connection) throws ResourceException {
	}

	/**
	 * @see ManagedConnection#addConnectionEventListener(ConnectionEventListener)
	 */
	public void addConnectionEventListener(ConnectionEventListener listener) {

		listeners.add(listener);
	}

	/**
	 * @see ManagedConnection#removeConnectionEventListener(ConnectionEventListener)
	 */
	public void removeConnectionEventListener(ConnectionEventListener listener) {

		listeners.remove(listener);
	}

	/**
	 * @see ManagedConnection#getXAResource()
	 */
	public XAResource getXAResource() throws ResourceException {

		throw new NotSupportedException(TRANSACTIONS_NOT_SUPPORTED_ERROR);
	}

	/**
	 * @see ManagedConnection#getLocalTransaction()
	 */
	public LocalTransaction getLocalTransaction() throws ResourceException {

		throw new NotSupportedException(TRANSACTIONS_NOT_SUPPORTED_ERROR);
	}

	/**
	 * @see ManagedConnection#getMetaData()
	 */
	public ManagedConnectionMetaData getMetaData() throws ResourceException {

		return new HelloWorldManagedConnectionMetaDataImpl(connection.getMetaData());
	}

	/**
	 * @see ManagedConnection#setLogWriter(PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws ResourceException {

		this.out = out;
	}

	/**
	 * @see ManagedConnection#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws ResourceException {

		return out;
	}

}