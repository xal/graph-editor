package com.jff.grapheditor.graph.editor.ui.state;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.jff.grapheditor.graph.editor.ui.GraphEditorComponent;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphEdge;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphElement;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphLine;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphNode;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;

public abstract class ModifyEdgeState extends AbstractState {

	protected GraphNode sourceNode;
	protected GraphNode destinationNode;
	
	private GraphLine modifiedEdgeLine;
	
	
	public static enum ModifyStateStep {
		MODIFY_SOURCE_NODE, MODIFY_DESTINATION_NODE;
	}
	
	private ModifyStateStep modifyEdgeState;
	
	
	
	public ModifyEdgeState(GraphEdge modifiedEdge) {
		super(StateType.MODIFY_EDGE);
		
		sourceNode = modifiedEdge.getSourceNode();
		
		modifyEdgeState =  ModifyStateStep.MODIFY_SOURCE_NODE;
		
		destinationNode = modifiedEdge.getDestinationNode();
		
		Point2D startPoint = sourceNode.getCenterWithOffset();
		Point2D endPoint = destinationNode.getCenterWithOffset();
		modifiedEdgeLine = new GraphLine(startPoint, endPoint);
	}
	
	


	public ModifyEdgeState(GraphNode sourceNode) {
		super(StateType.MODIFY_EDGE);
		this.sourceNode = sourceNode;
		
		modifyEdgeState =  ModifyStateStep.MODIFY_DESTINATION_NODE;
		
		Point2D startPoint = sourceNode.getCenterWithOffset();
		Point2D endPoint = new Point2D.Double(startPoint.getX(), startPoint.getY());
		modifiedEdgeLine = new GraphLine(startPoint, endPoint);
		
		

	}

	


	private PInputEventListener nodeSelectorHandler;
	private EdgeModifiyerEventHandler edgeModifiyerEventHandler;


	@Override
	public void install(GraphEditorComponent graphEditor, InteractionManager manager) {
		
		nodeSelectorHandler = new NodeSelectorEventHandler(graphEditor, manager);
		
		edgeModifiyerEventHandler = new EdgeModifiyerEventHandler(graphEditor, manager);
		
		PCamera camera = graphEditor.getCamera();
		PNode nodeLayer = graphEditor.getNodeLayer();
		PNode edgeLayer = graphEditor.getEdgeLayer();
		PNode popUpLayer = graphEditor.getPopUpLayer();
		PNode modifyLayer = graphEditor.getModifyLayer();
		
		modifyLayer.addChild(modifiedEdgeLine);
		
		nodeLayer.addInputEventListener(nodeSelectorHandler);
		
		modifyLayer.addInputEventListener(edgeModifiyerEventHandler);
		camera.addInputEventListener(edgeModifiyerEventHandler);
	}


	@Override
	public void unistall(GraphEditorComponent graphEditor, InteractionManager manager) {
	
		
		PCamera camera = graphEditor.getCamera();
		PNode nodeLayer = graphEditor.getNodeLayer();
		PNode edgeLayer = graphEditor.getEdgeLayer();
		PNode popUpLayer = graphEditor.getPopUpLayer();
		PNode modifyLayer = graphEditor.getModifyLayer();
		
		nodeLayer.removeInputEventListener(nodeSelectorHandler);
		modifyLayer.removeInputEventListener(edgeModifiyerEventHandler);
		
		modifyLayer.removeChild(modifiedEdgeLine);
		
		camera.removeInputEventListener(edgeModifiyerEventHandler);
		
	}
	

	public class EdgeModifiyerEventHandler extends PBasicInputEventHandler {
		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public EdgeModifiyerEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
			super();
			this.graphEditor = graphEditor;
			this.manager = manager;
		}

		@Override
		public void mouseMoved(PInputEvent event) {
			super.mouseMoved(event);

			Point2D position = event.getPosition();

			PNode modifiedChild = modifiedEdgeLine;

			GraphLine graphLine = (GraphLine) modifiedChild;
			
			switch (modifyEdgeState) {
			case MODIFY_SOURCE_NODE:
				graphLine.setStartPoint(position);	
				break;
				
			case MODIFY_DESTINATION_NODE:
				graphLine.setEndPoint(position);	
				break;

			default:
				break;
			}
			
			

			graphLine.update();
		}
	};
	
	public class NodeSelectorEventHandler extends PBasicInputEventHandler {
		private GraphEditorComponent graphEditor;
		private InteractionManager manager;
		
		
		
		public NodeSelectorEventHandler(GraphEditorComponent graphEditor, InteractionManager manager) {
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
			
			super.mousePressed(event);
			
			GraphElement graphElement = (GraphElement) event.getPickedNode();
			
			GraphNode graphNode = (GraphNode) graphElement;
			
			switch (modifyEdgeState) {
			case MODIFY_SOURCE_NODE:
				sourceNode = graphNode;
				modifyEdgeState = ModifyStateStep.MODIFY_DESTINATION_NODE;
				break;
				
			case MODIFY_DESTINATION_NODE:
				destinationNode = graphNode;
				finishModify(graphEditor, manager);
				break;

			default:
				break;
			}

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

	protected abstract void finishModify(GraphEditorComponent graphEditor, InteractionManager manager);


}
