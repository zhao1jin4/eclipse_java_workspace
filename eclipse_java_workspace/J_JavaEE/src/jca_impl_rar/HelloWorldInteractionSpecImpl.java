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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.resource.cci.InteractionSpec;

public class HelloWorldInteractionSpecImpl implements HelloWorldInteractionSpec {
	
	private String functionName;
	protected transient PropertyChangeSupport propertyChange;

	/**
	 * Constructor for HelloWorldInteractionSpecImpl
	 */
	public HelloWorldInteractionSpecImpl() {

		super();
	}

	/**
	 * Gets the functionName
	 * @return Returns a String
	 * @see HelloWorldInteractionSpec#getFunctionName()
	 */
	public String getFunctionName() {

		return functionName;
	}

	/**
	 * Sets the functionName
	 * @param functionName The functionName to set
	 * @see HelloWorldInteractionSpec#setFunctionName(String)
	 */
	public void setFunctionName(String functionName) {

		String oldFunctionName = functionName;
		this.functionName = functionName;
		firePropertyChange("FunctionName", oldFunctionName, functionName);
	}

	/**
	 * The addPropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {

		getPropertyChange().addPropertyChangeListener(listener);
	}

	/**
	 * The addPropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void addPropertyChangeListener(
		String propertyName,
		PropertyChangeListener listener) {
			
		getPropertyChange().addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * The firePropertyChange method was generated to support the propertyChange field.
	 */
	public void firePropertyChange(PropertyChangeEvent evt) {

		getPropertyChange().firePropertyChange(evt);
	}

	/**
	 * The firePropertyChange method was generated to support the propertyChange field.
	 */
	public void firePropertyChange(
		String propertyName,
		int oldValue,
		int newValue) {

		getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * The firePropertyChange method was generated to support the propertyChange field.
	 */
	public void firePropertyChange(
		String propertyName,
		Object oldValue,
		Object newValue) {

		getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * The firePropertyChange method was generated to support the propertyChange field.
	 */
	public void firePropertyChange(
		String propertyName,
		boolean oldValue,
		boolean newValue) {

		getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Accessor for the propertyChange field.
	 */
	protected PropertyChangeSupport getPropertyChange() {

		if (propertyChange == null) {
			propertyChange = new PropertyChangeSupport(this);
		}
		return propertyChange;
	}

	/**
	 * The hasListeners method was generated to support the propertyChange field.
	 */
	public synchronized boolean hasListeners(String propertyName) {

		return getPropertyChange().hasListeners(propertyName);
	}

	/**
	 * The removePropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {

		getPropertyChange().removePropertyChangeListener(listener);
	}

	/**
	 * The removePropertyChangeListener method was generated to support the propertyChange field.
	 */
	public synchronized void removePropertyChangeListener(
		String propertyName,
		PropertyChangeListener listener) {

		getPropertyChange().removePropertyChangeListener(propertyName, listener);
	}
}