package com.jff.grapheditor;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class Perspective implements IPerspectiveFactory {
	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		
		String editorArea = layout.getEditorArea();
		
		layout.addView(ElementInfoView.ID,IPageLayout.RIGHT, 0.75f, editorArea);
		
		layout.addView(ElementInfoView.ID, IPageLayout.RIGHT, 0.1f, ElementInfoView.ID);
		layout.addView(ElementEditView.ID, IPageLayout.BOTTOM, 0.1f, ElementEditView.ID);
	
	}

}
