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

public class CreateEdgeState extends ModifyEdgeState {
	

	public CreateEdgeState(GraphNode sourceNode) {
		super(sourceNode);
	
	}

	@Override
	protected void finishModify(GraphEditorComponent graphEditor, InteractionManager manager) {
		
		manager.createNewEdge(sourceNode, destinationNode);
		
	}

	


}
