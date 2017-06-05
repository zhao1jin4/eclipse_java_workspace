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
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceWarning;

public class HelloWorldInteractionImpl implements Interaction {

	private static final String CLOSED_ERROR = "Connection closed";
	private static final String INVALID_FUNCTION_ERROR = "Invalid function";
	private static final String INVALID_INPUT_ERROR =
		"Invalid input record for function";
	private static final String INVALID_OUTPUT_ERROR =
		"Invalid output record for function";
	private static final String OUTPUT_RECORD_FIELD_01 = "Hello World!";
	private static final String EXECUTE_WITH_INPUT_RECORD_ONLY_NOT_SUPPORTED =
		"execute() with input record only not supported";

	private Connection connection;
	private boolean valid;

	/**
	 * Constructor for HelloWorldInteractionImpl
	 */
	public HelloWorldInteractionImpl(Connection connection) {

		super();
		this.connection = connection;
		valid = true;
	}

	/**
	 * @see Interaction#close()
	 */
	public void close() throws ResourceException {

		connection = null;
		valid = false;
	}

	/**
	 * @see Interaction#getConnection()
	 */
	public Connection getConnection() {

		return connection;
	}

	/**
	 * @see Interaction#execute(InteractionSpec, Record, Record)
	 */
	public boolean execute(InteractionSpec ispec, Record input, Record output)
		throws ResourceException {

		if (valid) {
			if (((HelloWorldInteractionSpecImpl) ispec)
				.getFunctionName()
				.equals(HelloWorldInteractionSpec.SAY_HELLO_FUNCTION)) {
				if (input.getRecordName().equals(HelloWorldIndexedRecord.INPUT)) {
					if (output.getRecordName().equals(HelloWorldIndexedRecord.OUTPUT)) {
						((HelloWorldIndexedRecord) output).clear();
						((HelloWorldIndexedRecord) output).add(OUTPUT_RECORD_FIELD_01);
					} else {
						throw new ResourceException(INVALID_OUTPUT_ERROR);
					}
				} else {
					throw new ResourceException(INVALID_INPUT_ERROR);
				}

			} else {
				throw new ResourceException(INVALID_FUNCTION_ERROR);
			}
		} else {
			throw new ResourceException(CLOSED_ERROR);
		}
		return true;
	}

	/**
	 * @see Interaction#execute(InteractionSpec, Record)
	 */
	public Record execute(InteractionSpec ispec, Record input)
		throws ResourceException {

		throw new NotSupportedException(EXECUTE_WITH_INPUT_RECORD_ONLY_NOT_SUPPORTED);
	}

	/**
	* @see Interaction#getWarnings()
	*/
	public ResourceWarning getWarnings() throws ResourceException {

		return null;
	}

	/**
	* @see Interaction#clearWarnings()
	*/
	public void clearWarnings() throws ResourceException {
	}

}