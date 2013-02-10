package com.jff.grapheditor.graph.editor.ui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.widgets.Composite;

import com.jff.grapheditor.graph.editor.ui.components.objects.GraphEdge;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphElement;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphLine;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphNode;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphPopUpItem;
import com.jff.grapheditor.graph.editor.ui.components.objects.QuestEdge;
import com.jff.grapheditor.graph.editor.ui.components.objects.QuestNode;
import com.jff.grapheditor.graph.editor.ui.components.util.PSWTCanvasImproove;
import com.jff.grapheditor.graph.editor.ui.state.AbstractState;
import com.jff.grapheditor.graph.editor.ui.state.StateManager;
import com.jff.grapheditor.graph.editor.ui.state.ModifyEdgeState.EdgeModifiyerEventHandler;
import com.jff.questbase.model.factories.ActionsFactory;
import com.jff.questbase.model.factories.PagesFactory;
import com.jff.questbase.model.object.Action;
import com.jff.questbase.model.object.Page;
import com.jff.questcreator.model.InteractionManager;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.event.PInputEventListener;

public class GraphEditorComponent extends PSWTCanvasImproove {


	public void onElementStartDrag(GraphElement graphElement) {
		graphElement.onStartDrag(this);
	}

	public void onElementEndDrag(GraphElement graphElement) {
		graphElement.onDrag(this);
		
	}

	public void onElementDrag(GraphElement graphElement) {
		graphElement.onEndDrag(this);
		
	}
	
	public void performAction(GraphPopUpItem graphPopUpItem) {
		InteractionManager manager = this;
		graphPopUpItem.performAction(manager);
		
	}
	
	private AbstractState currentState;

	private InteractionManager manager;

	private PLayer popUpLayer;

	private PLayer nodeLayer;

	private PLayer edgeLayer;

	private PLayer modifyLayer;

	public AbstractState getCurrentState() {
		return currentState;
	}
	

	public void startCreateNewEdgeFrom(GraphNode sourceNode) {
		graphEditor.startCreateEdge(sourceNode);
		
		
	}

	public void startModifyEdge(GraphEdge graphEdge) {
		graphEditor.startModifyEdge(graphEdge);
		
	}


	public PLayer getNodeLayer() {
		return nodeLayer;
	}

	public PLayer getEdgeLayer() {
		return edgeLayer;
	}

	public PLayer getModifyLayer() {
		return modifyLayer;
	}

	public GraphEditorComponent(Composite parent, int style) {
		super(parent, style);

		GraphEditorComponent graphEditor = this;
		

		int width = 800;
		int height = 600;

		this.setSize(width, height);

		nodeLayer = new PLayer();
		popUpLayer = new PLayer();
		modifyLayer = new PLayer();
		edgeLayer = new PLayer();

		addLayerToEditor(edgeLayer);
		addLayerToEditor(nodeLayer);
		addLayerToEditor(popUpLayer);
		addLayerToEditor(modifyLayer);
		
		createTest();

		Collection<Page> pages = PagesFactory.getAllPages();

		for (Page page : pages) {
			Random rand = new Random();
			int x = rand.nextInt(100);
			int y = rand.nextInt(100);
			QuestNode node = new QuestNode(page, x, y, 20);
			nodeLayer.addChild(node);
		}

		Collection<Action> actions = ActionsFactory.getAllActions();

		for (Action action : actions) {

			long nodeSourceId = action.getSourcePageId();
			long nodeDestId = action.getDestPageId();

			QuestNode nodeSource = QuestNode.getNodeById(nodeSourceId);
			QuestNode nodeDest = QuestNode.getNodeById(nodeDestId);

			QuestEdge edge = new QuestEdge(action, nodeSource, nodeDest);

			edgeLayer.addChild(edge);
		}

		Iterator it = nodeLayer.getChildrenIterator();

		while (it.hasNext()) {
			QuestNode node = (QuestNode) it.next();
			node.update();
		}

		
		setNormalState();
		
		nodeLayer.moveToFront();
	}

	private void addLayerToEditor(PLayer layer) {
		PRoot root = getRoot();
		PCamera camera = getCamera();
		root.addChild(layer);
		camera.addLayer(layer);
	}

	public void setNormalState() {
		AbstractState normalState = StateManager.createNormalState();
		changeState(normalState);
	}

	private void changeState(AbstractState state) {
		if (this.currentState != state) {

			if (currentState != null) {
				this.currentState.unistall(this, manager);
			}

			this.currentState = state;

			state.install(this, manager);
		}

	}

	public PLayer getPopUpLayer() {
		return popUpLayer;
	}

	public void removeEdge(GraphEdge edge) {
		edgeLayer.removeChild(edge);

	}

	public void removeNode(GraphNode node) {
		nodeLayer.removeChild(node);

	}

	public void addEdge(GraphEdge edge) {
		edgeLayer.addChild(edge);

	}

	public void addNode(GraphNode node) {
		nodeLayer.addChild(node);

	}

	public void startCreateEdge(final GraphNode sourceNode) {

		AbstractState state = StateManager.createCreateEdgeState(sourceNode);
		changeState(state);

	}

	public void startModifyEdge(final GraphEdge graphEdge) {

		AbstractState state = StateManager.createModifyEdgeState(graphEdge);
		changeState(state);

	}

	private void createTest() {
		Page page1 = PagesFactory.createNewPage();
		Page page2 = PagesFactory.createNewPage();
		Page page3 = PagesFactory.createNewPage();

		Action action1 = ActionsFactory.createNewAction(page1, page2);
		Action action2 = ActionsFactory.createNewAction(page1, page2);
		Action action3 = ActionsFactory.createNewAction(page1, page2);
		// Action action4 = ActionsFactory.createNewAction(page1, page3);
		Action action5 = ActionsFactory.createNewAction(page2, page3);
		Action action6 = ActionsFactory.createNewAction(page3, page2);

		Action action7 = ActionsFactory.createNewAction(page3, page3);
		Action action8 = ActionsFactory.createNewAction(page3, page3);
		Action action9 = ActionsFactory.createNewAction(page3, page3);

	}
}