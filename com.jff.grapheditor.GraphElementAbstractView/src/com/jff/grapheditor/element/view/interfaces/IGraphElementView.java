package com.jff.grapheditor.element.view.interfaces;

import com.jff.grapheditor.types.AbstractElement;

public interface IGraphElementView {

	
	public void onElementSelected(AbstractElement abstractElement);
	public void onElementViewed(AbstractElement abstractElement);
	
	public void addElementListener(IElementListener elementListener);
	public void removeElementListener(IElementListener elementListener);
}
