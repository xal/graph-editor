package com.jff.grapheditor.element.view.interfaces;

import com.jff.grapheditor.types.AbstractElement;

public interface IElementListener {

	
	public void onElementSelected(AbstractElement element);
	public void onElementViewed(AbstractElement element);
}
