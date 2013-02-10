package com.jff.grapheditor.graph.editor.types;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.swt.PSWTPath;

public abstract class GraphElement extends PSWTPath {

	private final static Logger LOG = LoggerFactory.getLogger(GraphElement.class.getSimpleName());
	
	private boolean stateSelected;
	private boolean stateHovered;

	protected List<GraphPopUpItem> popUpItems = new ArrayList<GraphPopUpItem>();
	
	@Override
	protected void paint(PPaintContext paintContext) {
		// TODO Auto-generated method stub
		super.paint(paintContext);
	}

	public Point2D getCenterWithOffset() {
		Point2D center = getCenter();
		
		Point2D offset = getOffset();
		
		double x = center.getX();
		double y = center.getY();
		
		x += offset.getX();
		y += offset.getY();
		
		Point2D centerWithOffset = new Point2D.Double(x, y);
		
		return centerWithOffset;
		
	}
	
	public void onHoverExit(InteractionManager manager) {
		stateHovered = false;
		LOG.debug("UnHovered {}", this);
		
	}

	public void onSelectExit(InteractionManager manager) {
		stateSelected = false;
		clearPopUp(manager);
		LOG.debug("UnSelected {}", this);
	}

	public void onHover(InteractionManager manager) {
		stateHovered = true;
		LOG.debug("Hovered {}", this);
	}

	public void onSelect(InteractionManager manager) {
		stateSelected = true;
		showPopUp(manager);
		LOG.debug("Selected {}", this);
	}

	private void showPopUp(InteractionManager manager) {
		GraphElement selectedGraphElement = this;
		List<GraphPopUpItem> popUpItems = selectedGraphElement.createPopUpItems();
		
		this.popUpItems.addAll(popUpItems);
		
		updatePopUpItemsPositions();
		
		PNode popUpLayer = manager.getPopUpLayer();
		popUpLayer.addChildren(popUpItems);
	}

	protected List<GraphPopUpItem> createPopUpItems() {
		return null;
	}

	protected void updatePopUpItemsPositions() {
	}

	protected Point2D calculatePointForPopup() {
		return null;
	}

	public boolean isStateSelected() {
		return stateSelected;
	}

	public boolean isStateHovered() {
		return stateHovered;
	}
	
	
	public final List<GraphPopUpItem> getPopUpItems()  {
		return popUpItems ;
	}

	public void onDrag(InteractionManager manager) {

		updatePopUpItemsPositions();
		
	}
	
	public void clearPopUp(InteractionManager manager) {
		
		List<GraphPopUpItem> popUpItems = getPopUpItems();
		
		
		
		PNode popUpLayer = manager.getPopUpLayer();
		popUpLayer.removeChildren(popUpItems);
		
		this.popUpItems.clear();
		
	}

	public void onStartDrag(InteractionManager manager) {
		// TODO Auto-generated method stub
		
	}

	public void onEndDrag(InteractionManager manager) {
		// TODO Auto-generated method stub
		
	}
}
