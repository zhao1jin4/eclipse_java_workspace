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

import javax.resource.cci.ResourceAdapterMetaData;

public class HelloWorldResourceAdapterMetaDataImpl
	implements ResourceAdapterMetaData {

	private static final String ADAPTER_VERSION = "1.0";
	private static final String ADAPTER_VENDOR_NAME = "Willy Farrell";
	private static final String ADAPTER_NAME = "Hello World Resource Adapter";
	private static final String ADAPTER_DESCRIPTION =
		"A simple sample resource adapter";
	private static final String SPEC_VERSION = "1.0";
	private static final String[] INTERACTION_SPECS_SUPPORTED =
		{ "com.ibm.ssya.helloworldra.HelloWorldInteractionSpecImpl" };

	/**
	 * Constructor for HelloWorldResourceAdapterMetaDataImpl
	 */
	public HelloWorldResourceAdapterMetaDataImpl() {
		
		super();
	}

	/**
	 * @see ResourceAdapterMetaData#getAdapterVersion()
	 */
	public String getAdapterVersion() {

		return ADAPTER_VERSION;
	}

	/**
	 * @see ResourceAdapterMetaData#getAdapterVendorName()
	 */
	public String getAdapterVendorName() {

		return ADAPTER_VENDOR_NAME;
	}

	/**
	 * @see ResourceAdapterMetaData#getAdapterName()
	 */
	public String getAdapterName() {

		return ADAPTER_NAME;
	}

	/**
	 * @see ResourceAdapterMetaData#getAdapterShortDescription()
	 */
	public String getAdapterShortDescription() {

		return ADAPTER_DESCRIPTION;
	}

	/**
	 * @see ResourceAdapterMetaData#getSpecVersion()
	 */
	public String getSpecVersion() {

		return SPEC_VERSION;
	}

	/**
	 * @see ResourceAdapterMetaData#getInteractionSpecsSupported()
	 */
	public String[] getInteractionSpecsSupported() {
		
		return INTERACTION_SPECS_SUPPORTED;
	}

	/**
	 * @see ResourceAdapterMetaData#supportsExecuteWithInputAndOutputRecord()
	 */
	public boolean supportsExecuteWithInputAndOutputRecord() {
		
		return true;
	}

	/**
	 * @see ResourceAdapterMetaData#supportsExecuteWithInputRecordOnly()
	 */
	public boolean supportsExecuteWithInputRecordOnly() {
		
		return false;
	}

	/**
	 * @see ResourceAdapterMetaData#supportsLocalTransactionDemarcation()
	 */
	public boolean supportsLocalTransactionDemarcation() {

		return false;
	}

}