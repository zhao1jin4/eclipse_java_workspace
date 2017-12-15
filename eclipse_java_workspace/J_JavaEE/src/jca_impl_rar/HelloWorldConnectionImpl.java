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

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.Interaction;
import javax.resource.cci.LocalTransaction;
import javax.resource.cci.ResultSetInfo;
import javax.resource.spi.ManagedConnection;

public class HelloWorldConnectionImpl implements Connection {

	private static final String CLOSED_ERROR = "Connection closed";
	private static final String TRANSACTIONS_NOT_SUPPORTED =
		"Local transactions not supported";
	private static final String RESULT_SETS_NOT_SUPPORTED =
		"Result sets not supported";
	private boolean valid;

	private ManagedConnection mc;

	/**
	 * Constructor for HelloWorldConnectionImpl
	 */
	public HelloWorldConnectionImpl(ManagedConnection mc) {

		super();
		this.mc = mc;
		valid = true;
	}

	void invalidate() {

		mc = null;
		valid = false;
	}

	/**
	 * @see Connection#createInteraction()
	 */
	public Interaction createInteraction() throws ResourceException {

		if (valid) {
			return new HelloWorldInteractionImpl(this);
		} else {
			throw new ResourceException(CLOSED_ERROR);
		}
	}

	/**
	 * @see Connection#getLocalTransaction()
	 */
	public LocalTransaction getLocalTransaction() throws ResourceException {

		throw new NotSupportedException(TRANSACTIONS_NOT_SUPPORTED);
	}

	/**
	 * @see Connection#getMetaData()
	 */
	public ConnectionMetaData getMetaData() throws ResourceException {

		if (valid) {
			return new HelloWorldConnectionMetaDataImpl();
		} else {
			throw new ResourceException(CLOSED_ERROR);
		}
	}

	/**
	 * @see Connection#getResultSetInfo()
	 */
	public ResultSetInfo getResultSetInfo() throws ResourceException {

		throw new NotSupportedException(RESULT_SETS_NOT_SUPPORTED);
	}

	/**
	 * @see Connection#close()
	 */
	public void close() throws ResourceException {

		if (valid) {
			((HelloWorldManagedConnectionImpl) mc).close();
		}
	}

}