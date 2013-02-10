package com.jff.grapheditor.graph.editor.types;

import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jff.questcreator.grapheditor.utils.GeomertyUtils;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.swt.SWTGraphics2D;

public class GraphLine extends GraphElement {

	private static final int ARROW_X_OFFSET = 3;
	private static final int ARROW_Y_OFFSET = 7;
	private Point2D startPoint;
	private Point2D endPoint;

	private Polygon arrowPolygon = new Polygon();

	public GraphLine(Point2D startPoint, Point2D endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		update();
	}
	
	

	public Point2D getStartPoint() {
		return startPoint;
	}



	public void setStartPoint(Point2D startPoint) {
		this.startPoint = startPoint;
	}



	public Point2D getEndPoint() {
		return endPoint;
	}



	public void setEndPoint(Point2D endPoint) {
		this.endPoint = endPoint;
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

		Point2D midPoint = null;

		double sx = startPoint.getX();
		double sy = startPoint.getY();
		
		double ex = endPoint.getX();
		double ey = endPoint.getY();

		double diffx = ex - sx;
		double diffy = ey - sy;

		diffx = Math.abs(diffx);
		diffy = Math.abs(diffy);

		double minx = Math.min(sx, ex);
		double miny = Math.min(sy, ey);

		double midx = minx + diffx / 2;
		double midy = miny + diffy / 2;

		midPoint = new Point2D.Double(midx, midy);
		
		//System.out.println(startPoint+" "+midPoint+" "+endPoint);

		return midPoint;

	}

	public void update() {

		Point2D start = startPoint;
		Point2D end = endPoint;

		float x1, x2, y1, y2;

		x1 = (float) start.getX();
		y1 = (float) start.getY();
		x2 = (float) end.getX();
		y2 = (float) end.getY();

		Point2D mid = null;

		float tgAlpha;

		arrowPolygon.reset();

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

		mid = getMidPoint();

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

		int rotated[];

		rotated = GeomertyUtils.rotate(x - ARROW_X_OFFSET, y + ARROW_Y_OFFSET,
				alpha, xCenter, yCenter);
		arrowPolygon.addPoint(rotated[0], rotated[1]);
		rotated = GeomertyUtils.rotate(x, y, alpha, xCenter, yCenter);
		arrowPolygon.addPoint(rotated[0], rotated[1]);
		rotated = GeomertyUtils.rotate(x + ARROW_X_OFFSET, y + ARROW_Y_OFFSET,
				alpha, xCenter, yCenter);
		arrowPolygon.addPoint(rotated[0], rotated[1]);
		rotated = GeomertyUtils.rotate(x, y, alpha, xCenter, yCenter);
		arrowPolygon.addPoint(rotated[0], rotated[1]);
		rotated = GeomertyUtils.rotate(x - ARROW_X_OFFSET, y + ARROW_Y_OFFSET,
				alpha, xCenter, yCenter);
		arrowPolygon.addPoint(rotated[0], rotated[1]);

		java.awt.geom.Line2D shape = new Line2D.Double(startPoint, endPoint);
		
		this.setShape(shape);

		this.updatePopUpItemsPositions();

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
	protected Point2D calculatePointForPopup() {
		

		Point2D midPoint = getMidPoint();

		return midPoint;
	}

}
