package com.jff.grapheditor.element.view.interfaces;

import com.jff.grapheditor.types.AbstractElement;

public interface IElementListener {

	
	public void onElementSelected(AbstractElement element);
	public void onElementSelectExit(AbstractElement element);
	public void onElementViewed(AbstractElement element);
	public void onElementViewExit(AbstractElement element);
	public void onElementUpdated(AbstractElement element);
	public void onElementCreated(AbstractElement element);
	public void onElementDeleted(AbstractElement element);
}
