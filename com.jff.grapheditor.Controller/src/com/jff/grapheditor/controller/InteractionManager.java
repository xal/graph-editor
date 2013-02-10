package com.jff.grapheditor.controller;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jff.grapheditor.types.AbstractElement;
import com.jff.grapheditor.types.Edge;
import com.jff.grapheditor.types.Node;
import com.jff.questbase.model.factories.PagesFactory;
import com.jff.questbase.model.object.Page;
import com.jff.questcreator.elementinfo.ui.ElementInfoComponent;
import com.jff.questcreator.grapheditor.ui.GraphEditorComponent;
import com.jff.questcreator.grapheditor.ui.components.objects.Edge;
import com.jff.questcreator.grapheditor.ui.components.objects.GraphElement;
import com.jff.questcreator.grapheditor.ui.components.objects.GraphLine;
import com.jff.questcreator.grapheditor.ui.components.objects.Node;
import com.jff.questcreator.grapheditor.ui.components.objects.GraphPopUpItem;
import com.jff.questcreator.grapheditor.ui.components.objects.QuestEdge;
import com.jff.questcreator.grapheditor.ui.components.objects.QuestNode;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PBounds;

public class InteractionManager {

	private final static Logger LOG = LoggerFactory.getLogger(InteractionManager.class);
	
	private AbstractElement selectedGraphElement;
	private AbstractElement hoveredGraphElement;


	public AbstractElement getSelectedGraphElement() {
		return selectedGraphElement;
	}

	public void setSelectedGraphElement(AbstractElement selectedGraphElement) {
		if (this.selectedGraphElement == selectedGraphElement) {
			return;
		}
		clearSelectedElement();
		this.selectedGraphElement = selectedGraphElement;
		
		fireOnSelect(selectedGraphElement);

	}

	private void fireOnSelect(AbstractElement selectedGraphElement2) {
		// TODO Auto-generated method stub
		
	}

	public AbstractElement getHoveredGraphElement() {
		return hoveredGraphElement;
	}

	public void setHoveredGraphElement(AbstractElement hoveredGraphElement) {
		if (this.hoveredGraphElement == hoveredGraphElement) {
			return;
		}
		clearHoveredElement();
		this.hoveredGraphElement = hoveredGraphElement;		
		
		fireOnHover(hoveredGraphElement);
	}

	private void fireOnHover(AbstractElement hoveredGraphElement2) {
		// TODO Auto-generated method stub
		
	}

	public void clearSelectedElement() {
		if (selectedGraphElement != null) {
			fireOnSelectExit(selectedGraphElement);
			selectedGraphElement = null;			
		}

	}
	
	private void fireOnSelectExit(AbstractElement selectedGraphElement2) {
		// TODO Auto-generated method stub
		
	}


	public void clearHoveredElement() {
		if (hoveredGraphElement != null) {
		
			fireOnHoverExit();
			
			hoveredGraphElement = null;
		}
	}

	private void fireOnHoverExit() {
		// TODO Auto-generated method stub
		
	}


	public void deleteEdge(Edge edge) {
		
		deselectIfSelected(edge);
		
		Node sourceNode = edge.getSourceNode();
		Node destinationNode = edge.getDestinationNode();
		
		sourceNode.removeSourceEdge(edge);
		destinationNode.removeDestinationEdge(edge);
		
		sourceNode.update();
		destinationNode.update();
		
		fireEdgeRemoved(edge);
		
		
	}

	private void fireEdgeRemoved(Edge edge) {
		graphEditor.removeEdge(edge);
	}

	private void deselectIfSelected(AbstractElement graphElement) {
		if(graphElement == selectedGraphElement) {
			clearSelectedElement();
		}
		
		if(graphElement == hoveredGraphElement) {
			clearHoveredElement();
		}
		
	}



	public void deleteNode(Node node) {
		
		deselectIfSelected(node);
		
		
		
		List<Edge> sourceForEdges = new ArrayList<Edge>();		
		sourceForEdges.addAll(node.getSourceForEdges());
		
		for(Edge edge : sourceForEdges) {
			deleteEdge(edge);
		}
		
		List<Edge> destinationForEdges = new ArrayList<Edge>();
		destinationForEdges.addAll(node.getDestinationForEdges());
		
		for(Edge edge : destinationForEdges) {
			deleteEdge(edge);
		}
		
		fireNodeRemoved(node);
		
		
	}

	private void fireNodeRemoved(Node node) {
		graphEditor.removeNode(node);
	}

	public void createNewEdge(Node sourceNode, Node destinationNode) {
		QuestNode sourceQuestNode = (QuestNode) sourceNode;
		QuestNode destinationQuestNode = (QuestNode) destinationNode;
		
		
		// TODO: null
		QuestEdge questEdge = new QuestEdge(null, sourceQuestNode, destinationQuestNode);
		
		graphEditor.addEdge(questEdge);
		
		graphEditor.setNormalState();
	}

	public void editEdge(Edge graphEdge, Node sourceNode,
			Node destinationNode) {
		QuestNode sourceQuestNode = (QuestNode) sourceNode;
		QuestNode destinationQuestNode = (QuestNode) destinationNode;
		
		
		
		QuestEdge questEdge = (QuestEdge) graphEdge;
		
		questEdge.setSourceNode(sourceQuestNode);
		questEdge.setDestinationNode(destinationQuestNode);
		
		graphEditor.setNormalState();
		
	}

	
	

}
