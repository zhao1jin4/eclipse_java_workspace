package org.zhaojin.propertyEditor;

import java.beans.PropertyEditorSupport;

public class MyPropertyEditor extends PropertyEditorSupport
{
	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		String names []=text.split(",");
		Name n=new Name();
		n.setFirstname(names[0]);
		n.setMiddlename(names[1]);
		n.setLastname(names[2]);
		this.setValue(n);
	}
}
