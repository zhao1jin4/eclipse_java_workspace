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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.resource.cci.IndexedRecord;

public class HelloWorldIndexedRecordImpl implements HelloWorldIndexedRecord {

	private ArrayList list = new ArrayList();
	private String name;
	private String description;
	
	/**
	 * Constructor for HelloWorldIndexedRecordImpl
	 */
	public HelloWorldIndexedRecordImpl() {
		
		super();
	}

	/**
	 * @see Record#getRecordName()
	 */
	public String getRecordName() {
		
		return name;
	}

	/**
	 * @see Record#setRecordName(String)
	 */
	public void setRecordName(String name) {
		
		this.name = name;
	}

	/**
	 * @see Record#setRecordShortDescription(String)
	 */
	public void setRecordShortDescription(String description) {
		
		this.description = description;
	}

	/**
	 * @see Record#getRecordShortDescription()
	 */
	public String getRecordShortDescription() {
		
		return description;
	}

	/**
	 * @see List#size()
	 */
	public int size() {
		
		return list.size();
	}

	/**
	 * @see List#isEmpty()
	 */
	public boolean isEmpty() {
		
		return list.isEmpty();
	}

	/**
	 * @see List#contains(Object)
	 */
	public boolean contains(Object o) {
		
		return list.contains(o);
	}

	/**
	 * @see List#iterator()
	 */
	public Iterator iterator() {
		
		return list.iterator();
	}

	/**
	 * @see List#toArray()
	 */
	public Object[] toArray() {
		
		return list.toArray();
	}

	/**
	 * @see List#toArray(Object[])
	 */
	public Object[] toArray(Object[] a) {
		
		return list.toArray(a);
	}

	/**
	 * @see List#add(Object)
	 */
	public boolean add(Object o) {
		
		return list.add(o);
	}

	/**
	 * @see List#remove(Object)
	 */
	public boolean remove(Object o) {
		
		return list.remove(o);
	}

	/**
	 * @see List#containsAll(Collection)
	 */
	public boolean containsAll(Collection c) {
		
		return list.containsAll(c);
	}

	/**
	 * @see List#addAll(Collection)
	 */
	public boolean addAll(Collection c) {
		
		return list.addAll(c);
	}

	/**
	 * @see List#addAll(int, Collection)
	 */
	public boolean addAll(int index, Collection c) {
		
		return list.addAll(index, c);
	}

	/**
	 * @see List#removeAll(Collection)
	 */
	public boolean removeAll(Collection c) {
		
		return list.removeAll(c);
	}

	/**
	 * @see List#retainAll(Collection)
	 */
	public boolean retainAll(Collection c) {
		
		return list.retainAll(c);
	}

	/**
	 * @see List#clear()
	 */
	public void clear() {
		
		list.clear();
	}

	/**
	 * @see List#get(int)
	 */
	public Object get(int index) {
		
		return list.get(index);
	}

	/**
	 * @see List#set(int, Object)
	 */
	public Object set(int index, Object o) {
		
		return list.set(index, o);
	}

	/**
	 * @see List#add(int, Object)
	 */
	public void add(int index, Object o) {
		
		list.add(index, o);
	}

	/**
	 * @see List#remove(int)
	 */
	public Object remove(int index) {
		
		return list.remove(index);
	}

	/**
	 * @see List#indexOf(Object)
	 */
	public int indexOf(Object o) {
		
		return list.indexOf(o);
	}

	/**
	 * @see List#lastIndexOf(Object)
	 */
	public int lastIndexOf(Object o) {
		
		return list.lastIndexOf(o);
	}

	/**
	 * @see List#listIterator()
	 */
	public ListIterator listIterator() {
		
		return list.listIterator();
	}

	/**
	 * @see List#listIterator(int)
	 */
	public ListIterator listIterator(int index) {
		
		return list.listIterator(index);
	}

	/**
	 * @see List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		
		return list.subList(fromIndex, toIndex);
	}
	
	/**
	 * @see Record#clone()
	 */
	public Object clone() throws CloneNotSupportedException{
		
		throw new CloneNotSupportedException();
	}

}

