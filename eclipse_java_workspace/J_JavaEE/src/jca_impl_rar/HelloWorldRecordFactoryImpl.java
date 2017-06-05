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
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.RecordFactory;

public class HelloWorldRecordFactoryImpl implements RecordFactory {

	private static final String MAPPED_RECORD_NOT_SUPPORTED_ERROR = "Mapped record not supported";
	private static final String INVALID_RECORD_NAME = "Invalid record name";

	/**
	 * Constructor for HelloWorldRecordFactoryImpl
	 */
	public HelloWorldRecordFactoryImpl() {
		
		super();
	}

	/**
	 * @see RecordFactory#createMappedRecord(String)
	 */
	public MappedRecord createMappedRecord(String recordName)
		throws ResourceException {

		throw new NotSupportedException(MAPPED_RECORD_NOT_SUPPORTED_ERROR);
	}

	/**
	 * @see RecordFactory#createIndexedRecord(String)
	 */
	public IndexedRecord createIndexedRecord(String recordName)
		throws ResourceException {

		HelloWorldIndexedRecordImpl record = null;

		if ((recordName.equals(HelloWorldIndexedRecordImpl.INPUT))
			|| (recordName.equals(HelloWorldIndexedRecordImpl.OUTPUT))) {
			record = new HelloWorldIndexedRecordImpl();
			record.setRecordName(recordName);
		}
		if (record == null) {
			throw new ResourceException(INVALID_RECORD_NAME);
		} else {
			return record;
		}
	}

}