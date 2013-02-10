package com.jff.grapheditor.graph.editor.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class GraphEditorView extends ViewPart {
	public static final String ID = "com.jff.grapheditor.grapheditor.ui.View";

//	GraphEditorComponent graphEditor;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

//		graphEditor = new GraphEditorComponent(parent, SWT.NONE);

			new Label(parent, SWT.NONE).setText("graph");

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
//		graphEditor.setFocus();
	}
}