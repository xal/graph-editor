package com.jff.grapheditor.element.editor;

import org.eclipse.swt.widgets.Composite;

import com.jff.grapheditor.element.view.AbstractGraphElementView;
import com.jff.grapheditor.types.AbstractElement;

public class ElementEditView extends AbstractGraphElementView {
	public static final String ID = "com.jff.grapheditor.element.editor.ElementEditView";


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}

	@Override
	public void onElementSelected(AbstractElement abstractElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onElementViewed(AbstractElement abstractElement) {
		// TODO Auto-generated method stub
		
	}
}