package com.jff.grapheditor.graph.editor.types;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.swt.PSWTPath;
import edu.umd.cs.piccolox.swt.SWTGraphics2D;

public class GraphPopUpItem extends PSWTPath {

	private int RADIUS = 20;

	private String text;

	private GraphElement owner;
	
	public static interface Action {
		public void performAction(GraphElement owner, InteractionManager manager);
	}
	
	private Action action;


	public GraphPopUpItem(GraphElement owner, String text, Action action) {
		super();
		this.owner = owner;
		this.text = text;
		this.action = action;
	}
	
	public void performAction(InteractionManager manager) {
		action.performAction(owner, manager);
	}

	public void setPosition(double x, double y) {
		
		int r = RADIUS;
		
		int w = r;
		int h = r;
		Ellipse2D ellipse2d = new Ellipse2D.Double(x, y, w, h);
		this.setShape(ellipse2d);
		
		this.setPaint(Color.gray);
		
	}
	
	@Override
	protected void paint(PPaintContext paintContext) {
		super.paint(paintContext);

		final SWTGraphics2D g2 = (SWTGraphics2D) paintContext.getGraphics();

		Point2D center = this.getCenter();
		
		double x = center.getX();
		double y = center.getY();
		
		PBounds bounds = this.getBounds();
		
		x = bounds.getMinX();
		y = bounds.getMinY();
		
		x += 7;//bounds.getWidth() / 2;
		y += 3;//bounds.getHeight() / 2;
		
		g2.drawText(text, x, y);
		

	}

	public GraphElement getOwner() {
		return owner;
	}
	
	
	
}
