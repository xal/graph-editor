package com.jff.grapheditor.graph.editor.types;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jff.grapheditor.graph.editor.types.GraphPopUpItem.Action;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolox.swt.PSWTPath;

public class GraphNode extends GraphElement {

	private static final int POPUP_MARGIN = 25;
	private static final Paint COLOR_NORMAL = Color.white;
	private static final Paint COLOR_SELECTED = Color.blue;
	private static final Paint COLOR_HOVERED = Color.green;
	private List<GraphEdge> sourceForEdges = new ArrayList<GraphEdge>();
	private List<GraphEdge> destinationForEdges = new ArrayList<GraphEdge>();

	public GraphNode(int x, int y, int r) {
		super();
		int w = r;
		int h = r;
		Ellipse2D ellipse2d = new Ellipse2D.Float(x, y, w, h);
		this.setShape(ellipse2d);
		sourceForEdges = new ArrayList<GraphEdge>();
	}

	public void addSourceEdge(GraphEdge edge) {
		sourceForEdges.add(edge);
	}

	public void removeSourceEdge(GraphEdge edge) {
		sourceForEdges.remove(edge);
	}

	public void addDestinationEdge(GraphEdge edge) {
		destinationForEdges.add(edge);
	}

	public void removeDestinationEdge(GraphEdge edge) {
		destinationForEdges.remove(edge);
	}

	public void update() {

		int counterEdges;

		counterEdges = 0;

		for (GraphEdge edge : sourceForEdges) {
			edge.updateEdge(++counterEdges);
		}

		counterEdges = 0;

		for (GraphEdge edge : destinationForEdges) {
			edge.updateEdge(++counterEdges);
		}

	}

	private void changeColor(Paint color) {
		this.setPaint(color);

	}

	@Override
	public void onHoverExit(InteractionManager manager) {
		super.onHoverExit(manager);

		if (isStateSelected()) {
			changeColor(COLOR_SELECTED);
		} else {
			changeColor(COLOR_NORMAL);
		}

	}

	@Override
	public void onSelectExit(InteractionManager manager) {
		super.onSelectExit(manager);
		if (isStateHovered()) {
			changeColor(COLOR_HOVERED);
		} else {
			changeColor(COLOR_NORMAL);
		}

	}

	@Override
	public void onHover(InteractionManager manager) {

		super.onHover(manager);
		changeColor(COLOR_HOVERED);
	}

	@Override
	public void onSelect(InteractionManager manager) {

		super.onSelect(manager);

		changeColor(COLOR_SELECTED);

	}

	@Override
	public List<GraphPopUpItem> createPopUpItems() {

		List<GraphPopUpItem> items = new ArrayList<GraphPopUpItem>();

		GraphElement owner = this;
		
		Action addAction = new Action() {
			
			@Override
			public void performAction(GraphElement owner, InteractionManager manager) {
				
				GraphNode node = (GraphNode) owner;
				
				manager.startCreateNewEdgeFrom(node);
			}
		};
		
		Action removeAction = new Action() {
			
			@Override
			public void performAction(GraphElement owner, InteractionManager manager) {
				
				GraphNode node = (GraphNode) owner;
				
				manager.deleteNode(node);
				
			}
		};
		
		items.add(new GraphPopUpItem(owner, "+", addAction));
		items.add(new GraphPopUpItem(owner, "-", removeAction));

		return items;

	}

	@Override
	public void onDrag(InteractionManager manager) {

		super.onDrag(manager);
		update();
	}

	@Override
	public void onStartDrag(InteractionManager manager) {

		super.onStartDrag(manager);
	}

	@Override
	public void onEndDrag(InteractionManager manager) {

		super.onEndDrag(manager);

		update();
	}

	@Override
	protected Point2D calculatePointForPopup() {
		GraphElement selectedGraphElement = this;
		Point2D center = selectedGraphElement.getCenterWithOffset();

		double x = center.getX();
		double y = center.getY();

		x -= selectedGraphElement.getWidth();
		y -= selectedGraphElement.getHeight();

		Point2D point = new Point2D.Double(x, y);
		return point;
	}

	@Override
	protected void updatePopUpItemsPositions() {

		double x;
		double y;

		Point2D point = calculatePointForPopup();

		x = point.getX();
		y = point.getY();

		for (int i = 0; i < popUpItems.size(); i++) {
			GraphPopUpItem item = popUpItems.get(i);

			x += i * POPUP_MARGIN;

			item.setPosition(x, y);
		}

	}

	public List<GraphEdge> getSourceForEdges() {
		return sourceForEdges;
	}

	public List<GraphEdge> getDestinationForEdges() {
		return destinationForEdges;
	}

	
	
}
