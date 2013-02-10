package com.jff.grapheditor.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jff.grapheditor.types.AbstractElement;
import com.jff.grapheditor.types.Edge;
import com.jff.grapheditor.types.Node;

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
		
		fireEdgeRemoved(edge);
		
		
	}

	private void fireEdgeRemoved(Edge edge) {
//		graphEditor.removeEdge(edge);
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
		Collection<Edge> tempSourceForEdges = node.getSourceForEdges();
		sourceForEdges.addAll(tempSourceForEdges);
		
		for(Edge edge : sourceForEdges) {
			deleteEdge(edge);
		}
		
		List<Edge> destinationForEdges = new ArrayList<Edge>();
		Collection<Edge> tempDestinationForEdges = node.getDestinationForEdges();
		destinationForEdges.addAll(tempDestinationForEdges);
		
		for(Edge edge : destinationForEdges) {
			deleteEdge(edge);
		}
		
		fireNodeRemoved(node);
		
		
	}

	private void fireNodeRemoved(Node node) {
//		graphEditor.removeNode(node);
	}

	public void createNewEdge(Node sourceNode, Node destinationNode) {

		Edge edge = new Edge(sourceNode, destinationNode);
		
		fireEdgeCreated();
	}

	private void fireEdgeCreated() {
//		graphEditor.addEdge(questEdge);
//		
//		graphEditor.setNormalState();
	}

	public void editEdge(Edge graphEdge, Node sourceNode,
			Node destinationNode) {
		Edge questEdge = (Edge) graphEdge;
		
		questEdge.setSourceNode(sourceNode);
		questEdge.setDestinationNode(destinationNode);
		
		fireEdgeUpdated();
		
	}

	private void fireEdgeUpdated() {
//		graphEditor.setNormalState();
	}

	
	

}
