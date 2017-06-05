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
import javax.resource.cci.ConnectionMetaData;

public class HelloWorldConnectionMetaDataImpl implements ConnectionMetaData {

	private static final String PRODUCT_NAME = "Hello World EIS";
	private static final String PRODUCT_VERSION = "1.0";
	private static final String USER_NAME = "Not applicable";

	/**
	 * Constructor for HelloWorldConnectionMetaDataImpl
	 */
	public HelloWorldConnectionMetaDataImpl() {
		
		super();
	}

	/**
	 * @see ConnectionMetaData#getEISProductName()
	 */
	public String getEISProductName() throws ResourceException {
		
		return PRODUCT_NAME;
	}

	/**
	 * @see ConnectionMetaData#getEISProductVersion()
	 */
	public String getEISProductVersion() throws ResourceException {
		
		return PRODUCT_VERSION;
	}

	/**
	 * @see ConnectionMetaData#getUserName()
	 */
	public String getUserName() throws ResourceException {

		return USER_NAME;
	}

}