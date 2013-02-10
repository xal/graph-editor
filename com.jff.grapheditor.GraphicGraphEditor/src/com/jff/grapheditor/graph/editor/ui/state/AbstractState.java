package com.jff.grapheditor.graph.editor.ui.state;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.jff.grapheditor.graph.editor.ui.GraphEditorComponent;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphElement;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphLine;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphPopUpItem;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.event.PInputEventListener;

public abstract class AbstractState {
	protected final StateType stateType;

	public AbstractState(StateType stateType) {
		super();
		this.stateType = stateType;
	}

	public StateType getStateType() {
		return stateType;
	}

	public abstract void install(GraphEditorComponent graphEditor, InteractionManager manager);

	public abstract void unistall(GraphEditorComponent graphEditor, InteractionManager manager);

	protected void clearState(GraphEditorComponent graphEditor) {

		PCamera camera = graphEditor.getCamera();
		PNode nodeLayer = graphEditor.getNodeLayer();
		PNode edgeLayer = graphEditor.getEdgeLayer();
		PNode popUpLayer = graphEditor.getPopUpLayer();
		PNode modifyLayer = graphEditor.getModifyLayer();

		removeInputListener(camera);
		removeInputListener(nodeLayer);
		removeInputListener(edgeLayer);
		removeInputListener(popUpLayer);
		removeInputListener(modifyLayer);

	}

	protected void removeInputListener(PNode node) {
		PInputEventListener[] inputEventListeners = node
				.getInputEventListeners();
		for (PInputEventListener listener : inputEventListeners) {
			node.removeInputEventListener(listener);
		}
	}


	public static class PopUpPerformEventHandler extends PBasicInputEventHandler {

		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public PopUpPerformEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
			super();
			this.graphEditor = graphEditor;
			this.manager = manager;
		}
		
		@Override
		public void mousePressed(PInputEvent event) {
			// TODO Auto-generated method stub
			super.mousePressed(event);

			GraphPopUpItem graphPopUpItem = (GraphPopUpItem) event
					.getPickedNode();
			manager.performAction(graphPopUpItem);

		}

	};

	
}
