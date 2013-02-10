package com.jff.grapheditor.graph.editor.ui.state;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import com.jff.grapheditor.graph.editor.ui.GraphEditorComponent;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphElement;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.event.PInputEventListener;

public class NormalState extends AbstractState {

	private PInputEventListener cancelHandler;
	private PInputEventListener popupHandler;
	private PInputEventListener selectionHandler;
	private PInputEventListener nodeDraggerHandler;


	public NormalState() {
		super(StateType.NORMAL);

	}

	@Override
	public void install(GraphEditorComponent graphEditor, InteractionManager manager) {
		
		cancelHandler = new CameraCancelEventHandler(graphEditor, manager);
		popupHandler = new PopUpPerformEventHandler(graphEditor, manager);
		selectionHandler = new NodeEdgeSelecterEventHandler(graphEditor, manager);
		nodeDraggerHandler = new NodeDraggerEventHandler(graphEditor, manager);
		
		PCamera camera = graphEditor.getCamera();
		PNode nodeLayer = graphEditor.getNodeLayer();
		PNode edgeLayer = graphEditor.getEdgeLayer();
		PNode popUpLayer = graphEditor.getPopUpLayer();
		PNode modifyLayer = graphEditor.getModifyLayer();

		camera.addInputEventListener(cancelHandler);
		
		popUpLayer.addInputEventListener(popupHandler);


		edgeLayer.addInputEventListener(selectionHandler);
		nodeLayer.addInputEventListener(selectionHandler);

	
		nodeLayer.addInputEventListener(nodeDraggerHandler);
	}

	@Override
	public void unistall(GraphEditorComponent graphEditor, InteractionManager manager) {
		
		PCamera camera = graphEditor.getCamera();
		PNode nodeLayer = graphEditor.getNodeLayer();
		PNode edgeLayer = graphEditor.getEdgeLayer();
		PNode popUpLayer = graphEditor.getPopUpLayer();
		PNode modifyLayer = graphEditor.getModifyLayer();
		
		
		camera.removeInputEventListener(cancelHandler);
		
		popUpLayer.removeInputEventListener(popupHandler);


		edgeLayer.removeInputEventListener(selectionHandler);
		nodeLayer.removeInputEventListener(selectionHandler);

		
		nodeLayer.removeInputEventListener(nodeDraggerHandler);
		
	}
	
	public static class CameraCancelEventHandler extends PBasicInputEventHandler {

		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public CameraCancelEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
			super();
			this.graphEditor = graphEditor;
			this.manager = manager;
		}



		@Override
		public void mousePressed(PInputEvent event) {
			// TODO Auto-generated method stub
			super.mousePressed(event);

			PNode pickedNode = event.getPickedNode();

			PNode camera = graphEditor.getCamera();
			if (pickedNode == camera) {

				manager.clearSelectedElement();

			}

		}

	};

	public static class NodeDraggerEventHandler extends PDragEventHandler {
		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public NodeDraggerEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
			super();
			this.graphEditor = graphEditor;
			this.manager = manager;
		}

		
		{
			PInputEventFilter filter = new PInputEventFilter();
			filter.setAndMask(InputEvent.BUTTON1_MASK);
			setEventFilter(filter);
		}

		protected void startDrag(PInputEvent e) {
			super.startDrag(e);
			e.setHandled(true);

			GraphElement graphElement = (GraphElement) e.getPickedNode();

			graphElement.moveToFront();

			manager.onElementStartDrag(graphElement);
		}

		protected void drag(PInputEvent e) {
			super.drag(e);

			GraphElement graphElement = (GraphElement) e.getPickedNode();
			manager.onElementDrag(graphElement);

		}

		@Override
		protected void endDrag(PInputEvent e) {

			super.endDrag(e);

			GraphElement graphElement = (GraphElement) e.getPickedNode();
			manager.onElementEndDrag(graphElement);

		}

	};
	

	public static class NodeEdgeSelecterEventHandler extends PDragEventHandler {
		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public NodeEdgeSelecterEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
			super();
			this.graphEditor = graphEditor;
			this.manager = manager;
		}

		public void mouseEntered(PInputEvent e) {
			super.mouseEntered(e);

			if (e.getButton() == MouseEvent.NOBUTTON) {
				GraphElement graphElement = (GraphElement) e.getPickedNode();
				manager.setHoveredGraphElement(graphElement);
			}
		}

		@Override
		public void mousePressed(PInputEvent event) {
			// TODO Auto-generated method stub
			super.mousePressed(event);

			GraphElement graphElement = (GraphElement) event.getPickedNode();
			manager.setSelectedGraphElement(graphElement);

		}

		@Override
		public void mouseExited(PInputEvent e) {

			super.mouseExited(e);

			if (e.getButton() == MouseEvent.NOBUTTON) {
				GraphElement graphElement = (GraphElement) e.getPickedNode();
				manager.clearHoveredElement();

			}
		}

	};

}
