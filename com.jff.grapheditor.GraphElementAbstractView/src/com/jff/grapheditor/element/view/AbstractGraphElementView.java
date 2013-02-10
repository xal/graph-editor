package com.jff.grapheditor.element.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.part.ViewPart;

import com.jff.grapheditor.element.view.interfaces.IElementListener;
import com.jff.grapheditor.element.view.interfaces.IGraphElementView;
import com.jff.grapheditor.types.AbstractElement;

public abstract class AbstractGraphElementView extends ViewPart implements IGraphElementView {

	private List<IElementListener> elementListeners = new ArrayList<IElementListener>();

	public void addElementListener(IElementListener elementListener) {
		elementListeners.add(elementListener);
	}
	
	public void removeElementListener(IElementListener elementListener) {
		
		elementListeners.remove(elementListener);
		
	}
	
	public void fireElementViewed(AbstractElement element) {
		for(IElementListener listener : elementListeners) {
			listener.onElementViewed(element);
		}
	}
	
	public void fireElementSelected(AbstractElement element) {
		for(IElementListener listener : elementListeners) {
			listener.onElementSelected(element);
		}
	}

}