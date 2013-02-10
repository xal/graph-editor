package com.jff.grapheditor.graph.editor.types;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jff.grapheditor.graph.editor.types.GraphPopUpItem.Action;
import com.jff.questcreator.grapheditor.utils.GeomertyUtils;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.swt.PSWTPath;
import edu.umd.cs.piccolox.swt.SWTGraphics2D;

public class GraphEdge extends GraphElement {
	private static final float START_INDENT_SINGLE_NODE = 10;
	private static final int EDGE_INDENT_SINGLE_NODE = 5;
	private static final float START_INDENT_TWO_NODE = 15;
	private static final int EDGE_INDENT_TWO_NODE = 10;

	private static final Paint COLOR_NORMAL = Color.black;
	private static final Paint COLOR_SELECTED = Color.blue;
	private static final Paint COLOR_HOVERED = Color.green;

	private GraphNode sourceNode;
	private GraphNode destinationNode;

	public GraphNode getSourceNode() {
		return sourceNode;
	}

	public GraphNode getDestinationNode() {
		return destinationNode;
	}

	public void setSourceNode(GraphNode sourceNode) {
		this.sourceNode.removeSourceEdge(this);
		updateSourceNode();
		this.sourceNode = sourceNode;
		this.sourceNode.addSourceEdge(this);
		updateSourceNode();
	}

	private void updateSourceNode() {
		this.sourceNode.update();
	}

	public void setDestinationNode(GraphNode destinationNode) {
		this.destinationNode.removeDestinationEdge(this);
		updateDestinationNode();
		this.destinationNode = destinationNode;
		this.destinationNode.addDestinationEdge(this);
		updateDestinationNode();
	}

	private void updateDestinationNode() {
		this.destinationNode.update();
	}

	private final static int ARROW_X_OFFSET = 3;
	private final static int ARROW_Y_OFFSET = 7;

	private Polygon arrowPolygon = new Polygon();
	private int edgeNumberInChain;

	public GraphEdge(GraphNode sourceNode, GraphNode destinationNode) {
		super();
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;

		
		sourceNode.addSourceEdge(this);
		destinationNode.addDestinationEdge(this);
		
		update();
		
	}

	private void update() {
		updateSourceNode();
		updateDestinationNode();
		
	}

	@Override
	protected void paint(PPaintContext paintContext) {
		super.paint(paintContext);

		final SWTGraphics2D g2 = (SWTGraphics2D) paintContext.getGraphics();

		if (arrowPolygon != null) {
			g2.drawPolygon(arrowPolygon);
		}

	}

	public Point2D getMidPoint() {

		Point2D start = sourceNode.getFullBoundsReference().getCenter2D();
		Point2D end = destinationNode.getFullBoundsReference().getCenter2D();

		float x1, x2, y1, y2;

		x1 = (float) start.getX();
		y1 = (float) start.getY();
		x2 = (float) end.getX();
		y2 = (float) end.getY();

		Point2D mid = null;

		float tgAlpha;

		if (sourceNode == destinationNode) {
			mid = new Point2D.Double(x1 + ((x2 - x1) / 2), y1
					- START_INDENT_SINGLE_NODE - EDGE_INDENT_SINGLE_NODE
					* edgeNumberInChain);

		} else {

			float cathetusX = (float) Math.abs(x1 - x2);
			float cathetusY = (float) Math.abs(y1 - y2);

			tgAlpha = cathetusY / cathetusX;

			float alpha = (float) Math.toDegrees(Math.atan(tgAlpha));

			float percentsX = alpha / 90f;
			float percentsY = 1 - percentsX;

			boolean positiveX;

			boolean positiveY;

			if (x1 == x2) {
				if (y1 > y2) {
					positiveX = true;
					positiveY = false;
				} else {
					positiveX = false;
					positiveY = true;
				}
			} else if (x1 > x2) {
				if (y1 >= y2) {
					positiveX = true;
					positiveY = false;
				} else {
					positiveX = false;
					positiveY = false;
				}
			} else {
				if (y1 >= y2) {
					positiveX = true;
					positiveY = true;
				} else {
					positiveX = false;
					positiveY = true;
				}
			}

			mid = calcMid(edgeNumberInChain, x1, x2, y1, y2, percentsX,
					percentsY, positiveX, positiveY);

		}

		return mid;

	}

	public void updateEdge(int counterEdges) {

		this.edgeNumberInChain = counterEdges;

		Point2D start = sourceNode.getFullBoundsReference().getCenter2D();
		Point2D end = destinationNode.getFullBoundsReference().getCenter2D();

		float x1, x2, y1, y2;

		x1 = (float) start.getX();
		y1 = (float) start.getY();
		x2 = (float) end.getX();
		y2 = (float) end.getY();

		Point2D mid = null;

		float tgAlpha;

		arrowPolygon.reset();

		if (sourceNode == destinationNode) {
			mid = new Point2D.Double(x1 + ((x2 - x1) / 2), y1
					- START_INDENT_SINGLE_NODE - EDGE_INDENT_SINGLE_NODE
					* edgeNumberInChain);

			int x = (int) mid.getX();
			int y = (int) mid.getY();

			y -= START_INDENT_SINGLE_NODE + EDGE_INDENT_SINGLE_NODE
					* counterEdges;

			arrowPolygon.addPoint(x + ARROW_X_OFFSET, y + ARROW_Y_OFFSET);
			arrowPolygon.addPoint(x - ARROW_X_OFFSET, y);
			arrowPolygon.addPoint(x + ARROW_X_OFFSET, y - ARROW_Y_OFFSET);
			arrowPolygon.addPoint(x - ARROW_X_OFFSET, y);
			arrowPolygon.addPoint(x + ARROW_X_OFFSET, y + ARROW_Y_OFFSET);

		} else {

			float cathetusX = (float) Math.abs(x1 - x2);
			float cathetusY = (float) Math.abs(y1 - y2);

			tgAlpha = cathetusY / cathetusX;

			float alpha = (float) Math.toDegrees(Math.atan(tgAlpha));

			float percentsX = alpha / 90f;
			float percentsY = 1 - percentsX;

			boolean positiveX;

			boolean positiveY;

			if (x1 == x2) {
				if (y1 > y2) {
					positiveX = true;
					positiveY = false;
				} else {
					positiveX = false;
					positiveY = true;
				}
			} else if (x1 > x2) {
				if (y1 >= y2) {
					positiveX = true;
					positiveY = false;
				} else {
					positiveX = false;
					positiveY = false;
				}
			} else {
				if (y1 >= y2) {
					positiveX = true;
					positiveY = true;
				} else {
					positiveX = false;
					positiveY = true;
				}
			}

			mid = calcMid(counterEdges, x1, x2, y1, y2, percentsX, percentsY,
					positiveX, positiveY);

			int x = (int) mid.getX();
			int y = (int) mid.getY();

			int xCenter = x;
			int yCenter = y;

			cathetusX = (float) Math.abs(x1 - x2);
			cathetusY = (float) Math.abs(y1 - y2);

			tgAlpha = cathetusY / cathetusX;

			alpha = (float) Math.toDegrees(Math.atan(tgAlpha));

			if ((y1 < y2 && x1 < x2) || (y1 > y2 && x1 > x2)) {
				alpha += 270;
			} else {
				alpha = 90 - alpha;
			}

			if (y1 < y2) {
				alpha += 180;
			}

			alpha = (float) Math.toRadians(alpha);

			int ARROW_X_OFFSET = 3;
			int ARROW_Y_OFFSET = 7;

			// polygon = new Polygon();
			arrowPolygon.reset();

			int rotated[];

			rotated = GeomertyUtils.rotate(x - ARROW_X_OFFSET, y
					+ ARROW_Y_OFFSET, alpha, xCenter, yCenter);
			arrowPolygon.addPoint(rotated[0], rotated[1]);
			rotated = GeomertyUtils.rotate(x, y, alpha, xCenter, yCenter);
			arrowPolygon.addPoint(rotated[0], rotated[1]);
			rotated = GeomertyUtils.rotate(x + ARROW_X_OFFSET, y
					+ ARROW_Y_OFFSET, alpha, xCenter, yCenter);
			arrowPolygon.addPoint(rotated[0], rotated[1]);
			rotated = GeomertyUtils.rotate(x, y, alpha, xCenter, yCenter);
			arrowPolygon.addPoint(rotated[0], rotated[1]);
			rotated = GeomertyUtils.rotate(x - ARROW_X_OFFSET, y
					+ ARROW_Y_OFFSET, alpha, xCenter, yCenter);
			arrowPolygon.addPoint(rotated[0], rotated[1]);

		}

		Arc2D shape = GeomertyUtils.makeArc(new Point2D.Double(x1, y1), mid,
				new Point2D.Double(x2, y2));

		this.setShape(shape);

		this.updatePopUpItemsPositions();

	}

	public Point2D calcMid(int counterEdges, float x1, float x2, float y1,
			float y2, float percentsX, float percentsY, boolean positiveX,
			boolean positiveY) {
		Point2D mid;

		float x = x1 + ((x2 - x1) / 2);
		float y = y1 + ((y2 - y1) / 2);

		float indentX = percentsX
				* (START_INDENT_TWO_NODE + EDGE_INDENT_TWO_NODE * counterEdges);
		float indentY = percentsY
				* (START_INDENT_TWO_NODE + EDGE_INDENT_TWO_NODE * counterEdges);

		if (positiveX) {
			x += indentX;
		} else {
			x -= indentX;
		}
		if (positiveY) {
			y += indentY;
		} else {
			y -= indentY;
		}

		mid = new Point2D.Double(x, y);
		return mid;
	}

	private void changeColor(Paint color) {
		this.setStrokeColor(color);

	}

	@Override
	public void onSelect(InteractionManager manager) {

		super.onSelect(manager);

		changeColor(COLOR_SELECTED);

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
	public void onDrag(InteractionManager manager) {

		super.onDrag(manager);

	}

	@Override
	public void onStartDrag(InteractionManager manager) {

		super.onStartDrag(manager);
	}

	@Override
	public void onEndDrag(InteractionManager manager) {

		super.onEndDrag(manager);

	}

	@Override
	public List<GraphPopUpItem> createPopUpItems() {

		List<GraphPopUpItem> items = new ArrayList<GraphPopUpItem>();

		GraphElement owner = this;

		Action removeAction = new Action() {

			@Override
			public void performAction(GraphElement owner, InteractionManager manager) {

				GraphEdge edge = (GraphEdge) owner;

				manager.deleteEdge(edge);

			}
		};
		Action modifyAction = new Action() {

			@Override
			public void performAction(GraphElement owner, InteractionManager manager) {

				GraphEdge edge = (GraphEdge) owner;

				manager.startModifyEdge(edge);

			}
		};

		items.add(new GraphPopUpItem(owner, "*", modifyAction));
		items.add(new GraphPopUpItem(owner, "-", removeAction));

		return items;

	}

	@Override
	protected Point2D calculatePointForPopup() {
		PSWTPath selectedGraphElement = this;

		Point2D midPoint = getMidPoint();

		Point2D point = new Point2D.Double(midPoint.getX(), midPoint.getY());
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

			x += i * 25;

			item.setPosition(x, y);
		}

	}

}
